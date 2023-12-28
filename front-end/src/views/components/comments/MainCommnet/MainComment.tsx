import React, { ReactNode, useEffect, useState } from "react";

// style
import styles from "./MainComment.module.css";

// axios
import axios from "axios";

// component
import LikesButton from "../../post/LikesButton/LikesButton";
import SubComment from "../SubComment/SubComment";
import { Link } from "react-router-dom";

//
import { renderContentWithLinks } from "../../../../module/functions/renderContentWithTag/renderContentWithLinks";
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

type Comment = {
  commentId: number;
  content: string;
  createAt: Date;
  parentId?: number;
  uuid: string;
  nickname: string;
  profileImage: string;
};

type Props = {
  type?: "me";
  comment: Comment;
  setParentComment: React.Dispatch<React.SetStateAction<number | undefined>>;
  parentId: number | undefined;
  reRender?: () => void;
  reCount?: () => void;
};

const MainComment: React.FC<Props> = ({
  type,
  comment,
  setParentComment,
  parentId,
  reRender,
  reCount,
}) => {
  // store
  const UUID = useSelector((state: RootState) => state.user.UUID);
  // state
  const [subCommentsCount, setSubCommentsCount] = useState<number>(1);
  const [createdBefore, setCreatedBefore] = useState<string>("");
  const [subComments, setSubComments] = useState<Comment[]>([]);
  const [currentId, setCurrentId] = useState<number | undefined>();

  /* ============================================================================ */

  // rendering subComments
  const renderSubComments = () => {
    try {
      const node: ReactNode[] = [];

      if (currentId === comment.commentId) {
        subComments.map((e) => {
          if (e.uuid === UUID) {
            node.push(
              <SubComment
                type="me"
                key={e.commentId}
                comment={e}
                reCount={reCount}
                reSubCount={getSubCommentsCount}
                reRenderSub={getSubComments}
              />
            );
          } else {
            node.push(<SubComment key={e.commentId} comment={e} />);
          }
        });

        return node;
      }
      return;
    } catch (err) {
      console.error(err);
    }
  };

  // reply
  const reply = () => {
    if (parentId === comment.commentId) {
      setParentComment(undefined);
    }

    if (!(parentId === comment.commentId)) {
      setParentComment(comment.commentId);
    }
  };

  // reply toggle
  const renderingToggle = () => {
    if (currentId === comment.commentId) {
      setCurrentId(undefined);
      return;
    }
    setCurrentId(comment.commentId);
    getSubComments();
  };

  /* ============================================================================ */

  // axios
  const getSubCommentsCount = async () => {
    try {
      const params = { commentId: comment.commentId };

      const response = await axios.get("/api/post/comments/reply-cnt", {
        params: params,
      });

      setSubCommentsCount(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getSubComments = async () => {
    try {
      const params = {
        commentId: comment.commentId,
        page: 0,
        size: 10,
      };

      const response = await axios.get("/api/post/comments/reply", {
        params: params,
      });

      setSubComments(response.data.commentList);
    } catch (err) {
      console.error(err);
    }
  };

  const deleteComment = async () => {
    try {
      const data = {
        commentId: comment.commentId,
        uuid: UUID,
      };

      await axios.post("/api/post/comments/delete", data);

      if (reRender) reRender();
      if (reCount) reCount();
    } catch (err) {
      console.error(err);
    }
  };

  /* ============================================================================ */

  // exposing created at
  useEffect(() => {
    const commentCreatedAt = new Date(comment.createAt);
    const currentDate = new Date();

    const timeDiffernece = (currentDate.getTime() -
      commentCreatedAt.getTime()) as number;

    const seconds = Math.floor(timeDiffernece / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);

    let newTimeDifferenceString = "";

    if (days > 0) {
      newTimeDifferenceString = `${days}일 전`;
    } else if (hours > 0) {
      newTimeDifferenceString = `${hours}시간 전`;
    } else if (minutes > 1) {
      newTimeDifferenceString = `${minutes}분 전`;
    } else {
      newTimeDifferenceString = "방금 전";
    }

    setCreatedBefore(newTimeDifferenceString);
  }, [comment.createAt]);

  /* ============================================================================ */

  useEffect(() => {
    getSubCommentsCount();
  }, [comment.commentId]);

  return (
    <div className={styles.component} data-value={comment.commentId}>
      <div
        className={`${styles.comment} ${
          parentId === comment.commentId && styles.replyMode
        }`}
      >
        <Link
          to={`/profile/${comment.nickname}`}
          className={styles.comment__section_user}
        >
          <img
            className={styles.comment__section_user__profileImage}
            src={comment.profileImage}
          />
        </Link>
        <div className={styles.comment__section_content}>
          <div className={styles.comment__seciont_content__info}>
            <Link
              to={`/profile/${comment.nickname}`}
              className={styles.comment__section_content__info_nickname}
              data-value
            >
              {comment.nickname}
            </Link>
            <div className={styles.comment__section_content__info_date}>
              {createdBefore}
            </div>
          </div>
          <div className={styles.comment__section_content__content}>
            {renderContentWithLinks(comment.content)}
          </div>
          <div className={styles.comment__setSubComment} onClick={reply}>
            {parentId === comment.commentId ? "답글 취소" : "답글 달기"}
          </div>
          {type === "me" && (
            <div
              className={styles.comment__button_delete}
              onClick={deleteComment}
            >
              삭제
            </div>
          )}
        </div>
        <LikesButton type="comment" to={comment.commentId} />
      </div>
      {subCommentsCount > 0 && (
        <div
          onClick={renderingToggle}
          className={styles.comment__subCommentsCount}
        >
          {`- 답글 보기 (${subCommentsCount}) -`}
        </div>
      )}

      {subComments.length > 0 && (
        <div className={styles.box_subComments}>{renderSubComments()}</div>
      )}
    </div>
  );
};

export default MainComment;
