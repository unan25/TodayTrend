import React, { useEffect } from "react";

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
  comment: Comment;
};

const SubComment: React.FC<Props> = ({ comment }) => {
  useEffect(() => {}, []);

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
        </div>
        <LikesButton type="comment" to={comment.commentId} />
      </div>
    </div>
  );
};

export default SubComment;
