import React, { useEffect, useState } from 'react';
import styles from './Post.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { PostType } from 'interface/Postinterface';

type Prosps = {
  post: PostType;
  switch: boolean;
};

const Post: React.FC<any> = ({ post, swich }) => {
  //post 상세정보
  const [postdetails, setPostDetails] = useState();
  const navigate = useNavigate();

  //post 클릭시 함수 실행
  const handlePostClick = async () => {
    //함수 파람으로 받아오기
    //게시물 상세정보 가져오는 요청
    const res = await axios.get('/api/post');
    setPostDetails(res.data);
    navigate(`/post/${post.id}`);
  };

  return (
    <div>
      <img
        className={styles.post}
        src={post.url}
        alt={`게시물 ${post.id}`}
        onClick={swich ? handlePostClick : undefined}
      ></img>
    </div>
  );
};

export default Post;
