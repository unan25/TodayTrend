import React, { ReactNode, useEffect, useState } from "react";

// axios
import axios from "axios";

// state
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

// style
import styles from "./CommentsBox.module.css";

// components
import MainComment from "../MainCommnet/MainComment";
import TextWithHashtag from "../../../../views/components/post/TextWithHashtag/TextWithHashtag";

type Comment = {
  commentId: number;
  content: string;
  createAt: Date;
  parentId?: number;
  uuid: string;
  nickname: string;
  profileImage: string;
};

type CommentUplaod = {
  postId: string;
  uuid: string;
  parentId: number | undefined;
  content: string;
  userTagList: string[];
};

type Props = {
  postId: string | undefined;
};

const CommentsBox: React.FC<Props> = ({ postId }) => {
  // store
  const UUID = useSelector((state: RootState) => state.user.UUID);

  // state

  const [myComments, setMyComments] = useState<Comment[]>([]);

  const [mainComments, setMainComments] = useState<Comment[]>([]);

  const [content, setContent] = useState("");

  const [parentCommentId, setParentCommentId] = useState<number>();

  /* ============================================================================ */

  // rendering MainComments
  const renderMyComments = () => {
    try {
      const comments: ReactNode[] = [];

      myComments?.map((e, i) => {
        comments.push(
          <MainComment
            parentId={parentCommentId}
            key={e.commentId}
            comment={e}
            setParentComment={setParentCommentId}
          />
        );
      });

      return comments;
    } catch (err) {
      console.error(err);
    }
  };

  const renderMainComments = () => {
    try {
      const comments: ReactNode[] = [];

      mainComments.map((e, i) => {
        comments.push(
          <MainComment
            parentId={parentCommentId}
            key={e.commentId}
            comment={e}
            setParentComment={setParentCommentId}
          />
        );
      });

      return comments;
    } catch (err) {
      console.error(err);
    }
  };

  // comment
  function tagSplit(content: string) {
    const userTag: string[] = [];

    const temp = content.split(/\s+/);

    temp.map((e) => {
      if (e.includes("@")) {
        userTag.push(e.substring(1));
      }
    });

    return userTag;
  }

  /* ============================================================================ */

  // axios

  const getMyComments = async () => {
    try {
      const params = { postId: postId };

      const response = await axios.get(`/api/post/comments/${UUID}`, {
        params: params,
      });

      setMyComments(response.data.commentList);
    } catch (err) {
      console.error(err);
    }
  };

  const getMainComments = async () => {
    try {
      const params = { postId: postId, page: 0, size: 10, uuid: UUID };

      const response = await axios.get("/api/post/comments", {
        params: params,
      });

      setMainComments(response.data.commentList);
    } catch (err) {
      console.error(err);
    }
  };

  const postComment = async () => {
    if (content.length <= 0) {
      alert("댓글을 작성해주세용!");
      return;
    }

    try {
      const data: CommentUplaod = {
        postId: postId!,
        uuid: UUID,
        parentId: parentCommentId,
        content: content,
        userTagList: tagSplit(content),
      };

      const response = await axios.post("/api/post/comments", data);

      if (response.status === 201) {
        setContent("");
        setParentCommentId(undefined);
        getMyComments();
        getMainComments();
      }
    } catch (err) {
      console.error(err);
    }
  };

  /* ============================================================================ */

  useEffect(() => {
    getMyComments();
    getMainComments();
  }, [postId]);

  return (
    <div className={styles.component_body}>
      <div className={styles.commentsBox}>
        {myComments.length > 0 && renderMyComments()}
        {mainComments.length > 0 && renderMainComments()}
      </div>
      <TextWithHashtag
        type="comment"
        content={content}
        setContent={setContent}
        submitComment={postComment}
        placeHolder={parentCommentId ? "답글 달기" : "댓글 쓰기"}
      />
    </div>
  );
};

export default CommentsBox;
