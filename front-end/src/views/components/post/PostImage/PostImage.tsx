import React, { ReactComponentElement, useEffect, useState } from 'react';
import styles from './PostImage.module.css';

interface PostProps {
  postId: number;
  imageUrl: string;
  onClick: () => void;
}

const PostImage: React.FC<PostProps> = ({ postId, imageUrl, onClick }) => (
  <div key={postId} onClick={onClick} className={styles.post}>
    <img src={imageUrl} alt={`Post ${postId}`} className={styles.postImage} />
  </div>
);

export default PostImage;
