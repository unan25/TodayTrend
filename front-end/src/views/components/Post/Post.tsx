import React, { ReactComponentElement, useEffect, useState } from 'react';
import styles from './Post.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { PostType } from 'interface/Postinterface';

type Prosps = {
  post: PostType;
};

const Post: React.FC<any> = ({ post }) => {
  //post 상세정보
  const navigate = useNavigate();

  //post 클릭시 함수 실행
  const handlePostClick = async () => {
    //함수 파람으로 받아오기
    //게시물 상세정보 가져오는 요청
    navigate(`/post/${post.postId}`);
  };

  return (
    <div>
      <img
        className={styles.post}
        src={post.url}
        alt={`게시물 ${post.postId}`}
        onClick={handlePostClick}
      ></img>
    </div>
  );
};

export default Post;
