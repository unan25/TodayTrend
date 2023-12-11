import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import PostImage from '../PostImage/PostImage';
import styles from './PostList.module.css';

interface PostListProps {
  data: { pages: { data: { postId: number; imageUrl: string }[] }[] };
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
    pages: { data: { postId: number; imageUrl: string }[] }[];
  }) => {
    return data.pages.map((page, pageIndex) => (
      <React.Fragment key={pageIndex}>
        {page.data.map((post) => (
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
    <InfiniteScroll
      hasMore={hasNextPage}
      loadMore={fetchNextPage}
      className={styles.postList}
    >
      {data !== undefined ? renderData(data) : <div></div>}
    </InfiniteScroll>
  );
};

export default PostList;
