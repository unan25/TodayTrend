import React, { ReactNode, useEffect, useRef, useState } from "react";
//
import { Link, useParams } from "react-router-dom";

//
import axios from "axios";
import PostLikesButton from "../../../views/components/post/PostLikesButton/PostLikesButton";

//
import styles from "./PostDetailPage.module.css";

//

type PostDetail = {
  postId: number;
  postUserUUID: string;
  profileImage: string;
  nickName: string;
  content: string;
  createdAt: string;
  postImgs: string[];
};

type Category = {
  id: number;
  name: string;
};

const PostDetailPage: React.FC = () => {
  const [postDetail, setPostDetail] = useState<PostDetail>({
    postId: 0,
    postUserUUID: "",
    profileImage: "",
    nickName: "",
    content: "",
    createdAt: "",
    postImgs: [],
  });

  const [currentImage, setCurrentImage] = useState<number>(0);
  const [boxShadow, setBoxShadow] = useState<string>();
  const [category, setCategory] = useState<Category[]>([
    { name: "스포티", id: 1 },
  ]);

  const { postId } = useParams();

  /* ================================================================ */
  // contents
  const renderContentWithLinks = () => {
    const words = postDetail!.content.split(/\s+/);

    return words.map((word, index) => {
      if (word.startsWith("@")) {
        const username = word.substring(1);

        return (
          <a
            key={index}
            className={styles.userTag}
            href={`/profile/${username}`}
          >
            {word}{" "}
          </a>
        );
      } else if (word.startsWith("#")) {
        const hashtag = word.substring(1);
        return (
          <a key={index} className={styles.hashTag} href={`/search/${hashtag}`}>
            {word}{" "}
          </a>
        );
      } else {
        return <span key={index}>{word} </span>;
      }
    });
  };

  // image index
  const renderImageIndex = (length: number) => {
    let pages: ReactNode[] = [];

    for (let i = 0; i < length; i++) {
      pages.push(
        <div
          key={i}
          onClick={() => setCurrentImage(i)}
          className={`${
            styles.post_body_section1__image_controller__iconBox__icon
          } ${currentImage === i ? styles.checked : ""}`}
        ></div>
      );
    }

    return pages;
  };

  const renderCateogory = () => {
    const categories: ReactNode[] = [];

    category.map((e, i) => {
      categories.push(
        <div
          className={
            styles.post_body_section2__contentBox__box_category__category
          }
          key={i}
        >
          {e.name}
        </div>
      );
    });

    return categories;
  };
  /* ================================================================ */

  /* ================================================================ */
  // axios
  const getPostDetails = async () => {
    try {
      const response = await axios.get(`/api/post/postdetail?postId=${postId}`);
      setPostDetail({ ...response.data });
    } catch (err) {
      console.error(err);
    }
  };

  const getCategory = async () => {
    try {
      const response = await axios.get(`/api/post/category?postId=${postId}`);
      setCategory(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    getPostDetails();
    getCategory();
  }, [postId]);

  /* ================================================================ */

  return (
    <div className="page-body">
      <div className={styles.post_header}>
        <div className={styles.post_header__profile}>
          <img
            src={postDetail.profileImage}
            className={styles.post_header__profile_image}
          />
          <Link
            to={`/profile/${postDetail.nickName}`}
            className={styles.post_header__profile_nickNamenp}
          >
            {postDetail.nickName}
          </Link>
        </div>
      </div>
      <div className={styles.post_body}>
        <div className={styles.post_body_section1}>
          <img
            src={postDetail.postImgs[currentImage]}
            alt={postDetail.postImgs[currentImage]}
            className={styles.post_body_section1__post_image}
          />
          <div className={styles.post_body_section1__image_controller}>
            <button
              onClick={() => setCurrentImage((prev) => Math.max(0, prev - 1))}
            >
              이전
            </button>
            <div
              className={styles.post_body_section1__image_controller__iconBox}
            >
              {renderImageIndex(postDetail.postImgs.length)}
            </div>
            <button
              onClick={() =>
                setCurrentImage((prev) =>
                  Math.min(postDetail.postImgs.length - 1, prev + 1)
                )
              }
            >
              다음
            </button>
          </div>
        </div>
        <div className={styles.post_body_section2}>
          <div className={styles.post_body_section2_header}>
            <div className={styles.post_body_section2_header__dateBox}>
              {postDetail.createdAt}
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
              {renderContentWithLinks()}
            </div>
            <div className={styles.post_body_section2__contentBox__box}>
              <div
                className={styles.post_body_section2__contentBox__box_category}
              >
                {renderCateogory()}
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
                <PostLikesButton />
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
