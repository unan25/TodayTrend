import React, { useEffect, useState } from 'react';
import styles from './Post.module.css';
import axios from 'axios';
import Post from '../Post/Post';
import { PostType } from 'interface/Postinterface';

const PostList: React.FC<any> = ({ postList }) => {
  return (
    <div className="container ">
      <div className="row " style={{ width: '80%' }}>
        {postList &&
          postList.map((post: PostType) => (
            <div key={post.id} className="col-md-4 mb-3">
              <Post post={post}></Post>
            </div>
          ))}
      </div>
    </div>
  );
};

export default PostList;
