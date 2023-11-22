import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';

const PostDetailPage: React.FC = () => {
  // Post Detail 요청

  const { postId } = useParams();
  console.log(postId); // postId가 정상적으로 출력되는지 확인

  return (
    <div className="page-body">
      피드 상세페이지
      <div className="프로필"></div>
      <div className="큰박스">
        <div className="사진"> </div>
      </div>
      <div className="큰박스">
        <div className="포스트내용"> </div>
        <div className="댓글"></div>
      </div>
    </div>
  );
};

export default PostDetailPage;
