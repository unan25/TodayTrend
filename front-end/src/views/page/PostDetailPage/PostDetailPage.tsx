import React, { useEffect } from 'react';
import PostDetail from '../../components/PostDetail/PostDetail';

const PostDetailPage: React.FC = () => {
  // Post Detail 요청
  useEffect(() => {}, []);
  return (
    <div className="page-body">
      피드 상세페이지
      <PostDetail></PostDetail>
    </div>
  );
};

export default PostDetailPage;
