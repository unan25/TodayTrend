import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import PostImage from '../PostImage/PostImage';
import styles from './PostList.module.css';

interface PostListProps {
  data?: { pages?: { data?: { postId: number; imageUrl: string }[] }[] };
  fetchNextPage: () => void;
  hasNextPage?: boolean;
  navigate: (path: string) => void;
}

const PostList: React.FC<PostListProps> = ({
  data,
  fetchNextPage,
  hasNextPage,
  navigate,
}) => {
  const renderData = (data: {
    pages?: { data?: { postId: number; imageUrl: string }[] }[];
  }) => {
    return data?.pages?.map((page, pageIndex) => (
      <React.Fragment key={pageIndex}>
        {page?.data?.map((post) => (
          <PostImage
            key={post.postId}
            postId={post.postId}
            imageUrl={post.imageUrl}
            onClick={() => navigate(`/post/${post.postId}`)}
          />
        ))}
      </React.Fragment>
    ));
  };
  return (
    <div className={styles.component_body}>
      <InfiniteScroll
        hasMore={hasNextPage}
        loadMore={fetchNextPage}
        className={styles.postList}
      >
        {data ? renderData(data) : <div>현재 게시물이 없습니다.</div>}
      </InfiniteScroll>
    </div>
  );
};

export default PostList;
