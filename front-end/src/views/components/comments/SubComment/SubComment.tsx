import React, { useEffect } from "react";

// axios
import axios from "axios";

// state
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

// style
import styles from "./SubComment.module.css";

// component
import { Link } from "react-router-dom";
import LikesButton from "../../../../views/components/post/LikesButton/LikesButton";

// module
import { renderContentWithLinks } from "../../../../module/functions/renderContentWithTag/renderContentWithLinks";

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
  reCount?: () => void;
  reSubCount?: () => void;
  reRenderSub?: () => void;
};

const SubComment: React.FC<Props> = ({
  type,
  comment,
  reCount,
  reSubCount,
  reRenderSub,
}) => {
  const UUID = useSelector((state: RootState) => state.user.UUID);

  const deleteComment = async () => {
    try {
      const data = {
        commentId: comment.commentId,
        uuid: UUID,
      };

      const response = await axios.post("/api/post/comments/delete", data);

      if (response.status === 200) {
        if (reCount) reCount();
        if (reRenderSub) reRenderSub();
        if (reSubCount) reSubCount();
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className={styles.component} data-value={comment.commentId}>
      <div className={styles.comment}>
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
          </div>
          <div className={styles.comment__section_content__content}>
            {renderContentWithLinks(comment.content)}
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
    </div>
  );
};

export default SubComment;
