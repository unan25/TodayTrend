import React, { useEffect, useState } from "react";

//
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

//
import { useParams } from "react-router-dom";

//
import styles from "./PostLikesButton.module.css";
import axios from "axios";

function PostLikesButton() {
  const [likesCount, setLikesCount] = useState<number>(0);
  const [hasLiked, setHasLiked] = useState<boolean>(false);
  const [clicked, setClicked] = useState<boolean>();

  const { postId } = useParams();
  const UUID = useSelector((state: RootState) => state.user.UUID);

  const likesHandler = async () => {
    try {
      const data = {
        uuid: UUID,
        postId: postId,
      };

      const response = await axios.put("/api/post/like", data);

      setClicked(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getLikesCount = async () => {
    try {
      const response = await axios.get(`/api/post/likecnt?postId=${postId}`);

      setLikesCount(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getLiked = async () => {
    try {
      const response = await axios.get(
        `/api/post/liked?uuid=${UUID}&postId=${postId}`
      );

      setHasLiked(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    getLikesCount();
    getLiked();
  }, [clicked]);

  return (
    <div
      className={styles.post_body_section2__contentBox__box_detail__likesBox}
    >
      <div
        onClick={likesHandler}
        id={styles.heart}
        className={`${
          styles.post_body_section2__contentBox__box_detail__likesBox__btn_likes
        } ${clicked ? styles.clicked : ""} ${hasLiked ? styles.liked : ""}`}
      ></div>
      <div
        className={
          styles.post_body_section2__contentBox__box_detail__likesBox__count_likes
        }
      >
        {likesCount}
      </div>
    </div>
  );
}

export default PostLikesButton;
