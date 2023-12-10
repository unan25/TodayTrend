import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import styles from "./PostDetailPage.module.css";

const PostDetailPage: React.FC = () => {
  return (
    <div className="page-body">
      <div className={styles.post_header}>
        <div className={styles.post_header__profile}>
          <img
            src="https://todaytrend.s3.ap-northeast-2.amazonaws.com/profile/04dbd59a-c0e5-459c-bb2a-3b672e28c373TT_Default_Profile.jpg"
            className={styles.post_header__profile_image}
          />
          <div className={styles.post_header__profile_nickName}>닉네임</div>
        </div>
      </div>
      <div className={styles.post_body}>
        <div className={styles.post_body_section1}>
          <img
            src="https://todaytrend.s3.ap-northeast-2.amazonaws.com/profile/04dbd59a-c0e5-459c-bb2a-3b672e28c373TT_Default_Profile.jpg"
            alt="#"
            className={styles.post_body_section1__post_image}
          />
        </div>
        <div className={styles.post_body_section2}>
          <div className={styles.post_body_section2_header}>
            <div className={styles.post_body_section2_header__dateBox}>
              업로드 일자
            </div>
            <div className={styles.post_body_section2_header__buttonBox}>
              <button
                className={
                  styles.post_body_section2_header__buttonBox__button_update
                }
              >
                수정
              </button>
              <button
                className={
                  styles.post_body_section2_header__buttonBox__button_delete
                }
              >
                삭제
              </button>
            </div>
          </div>
          <div className={styles.post_body_section2__contentBox}>
            <div className={styles.post_body_section2__contentBox__content}>
              content
            </div>
            <div className={styles.post_body_section2__contentBox__box}>
              <div
                className={styles.post_body_section2__contentBox__box_category}
              >
                <div
                  className={
                    styles.post_body_section2__contentBox__box_category__category
                  }
                >
                  category
                </div>
              </div>
              <div
                className={styles.post_body_section2__contentBox__box_detail}
              >
                <div
                  className={
                    styles.post_body_section2__contentBox__box_detail__commentsBox
                  }
                >
                  <div
                    className={
                      styles.post_body_section2__contentBox__box_detail__commentsBox__btn_comments
                    }
                  >
                    comments
                  </div>
                  <div
                    className={
                      styles.post_body_section2__contentBox__box_detail__commentsBox__count_comments
                    }
                  >
                    5
                  </div>
                </div>
                <div
                  className={
                    styles.post_body_section2__contentBox__box_detail__likesBox
                  }
                >
                  <div
                    className={
                      styles.post_body_section2__contentBox__box_detail__commentsBox__btn_likes
                    }
                  >
                    likes
                  </div>
                  <div
                    className={
                      styles.post_body_section2__contentBox__box_detail__commentsBox__count_likes
                    }
                  >
                    20
                  </div>
                </div>
              </div>
            </div>
            <div className={styles.post_body_section2__contentBox__comments}>
              comments
            </div>
            <input
              placeholder="댓글"
              className={styles.post_body_section2__contentBox__comments_input}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default PostDetailPage;
