import React, { ReactNode, useEffect, useRef, useState } from 'react';
//
import { Link, useParams, useNavigate } from 'react-router-dom';

//
import axios from 'axios';
import LikesButton from '../../components/post/LikesButton/LikesButton';

//
import styles from './PostDetailPage.module.css';
import commentIcon from '../../../images/comment/comments.png';

// component
import CommentsBox from '../../../views/components/comments/CommentsBox/CommentsBox';

//
import { renderContentWithLinks } from '../../../module/functions/renderContentWithTag/renderContentWithLinks';

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
  // state
  const [postDetail, setPostDetail] = useState<PostDetail>({
    postId: 0,
    postUserUUID: '',
    profileImage: '',
    nickName: '',
    content: '',
    createdAt: '',
    postImgs: [],
  });

  const [currentImage, setCurrentImage] = useState<number>(0);

  const [category, setCategory] = useState<Category[]>([]);

  const [commentsCount, setCommentsCount] = useState<number>(0);

  const { postId } = useParams();

  const navigate = useNavigate();

  /* ================================================================ */

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
          } ${currentImage === i ? styles.checked : ''}`}
        ></div>
      );
    }

    return pages;
  };

  // category
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

  const getCommentsCount = async () => {
    try {
      const params = { postId: postId };

      const response = await axios.get('/api/post/comments/cnt', {
        params: params,
      });

      setCommentsCount(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  // ----------------------------추가------------------------------

  const deletePost = async () => {
    try {
      const [response1, response2, response3] = await Promise.all([
        axios.delete(`/api/post?postId=${postId}`),
        axios.delete(`/api/images/${postId}`),
        axios.delete(`/api/post/comments/${postId}`),
      ]);
      navigate(-1);
    } catch (e) {
      console.error(e);
    }
  };

  const editPost = () => {
    navigate(`/edit-post/${postId}`, { state: postDetail });
  };

  /* ================================================================ */
  // effecnt

  useEffect(() => {
    getPostDetails();
    getCategory();
    getCommentsCount();
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
                onClick={editPost}
              >
                수정
              </button>
              <button
                className={
                  styles.post_body_section2_header__buttonBox__button_delete
                }
                onClick={deletePost}
              >
                삭제
              </button>
            </div>
          </div>
          <div className={styles.post_body_section2__contentBox}>
            <div className={styles.post_body_section2__contentBox__content}>
              {renderContentWithLinks(postDetail.content)}
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
                  <img
                    src={commentIcon}
                    className={
                      styles.post_body_section2__contentBox__box_detail__commentsBox__icon_comments
                    }
                  />
                  <div
                    className={
                      styles.post_body_section2__contentBox__box_detail__commentsBox__count_comments
                    }
                  >
                    {commentsCount}
                  </div>
                </div>
                <LikesButton type="post" />
              </div>
            </div>
            <CommentsBox postId={postId} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default PostDetailPage;
