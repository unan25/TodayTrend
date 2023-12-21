// react
import React, { useEffect, useState } from 'react';

// react-query
import { useInfiniteQuery } from 'react-query';
// react-bootstrap
// styles
import styles from './SearchHashTagPage.module.css';
// components
import PostList from '../../components/post/PostList/PostList';
// type

// axios
import axios from 'axios';
// router
import { useNavigate, useLocation, useParams } from 'react-router-dom';
import { Nav } from 'react-bootstrap';

// import { sessionStorage } from 'redux-persist/es/storage/session';

export interface RequestTag {
  //post요청 body로
  hashtag?: string;
  page: number;
  size: number;
  tab: number;
}

function SearchHashTagPage() {
  const { tag } = useParams();
  const navigate = useNavigate();
  const [tab, setTab] = useState<number>(1);

  useEffect(() => {
    fetchPost({ hashtag: tag, page: 0, size: 6, tab: tab });
  }, []);

  //무한 스크롤
  const fetchPost = async ({ page, size, hashtag }: RequestTag) => {
    try {
      const requestBody = {
        page,
        size,
        hashtag,
      };
      // axios.post를 사용하여 데이터를 body에 실어서 요청 보내기
      const response = await axios.post('/api/post/search', requestBody);
      refetch();

      return response.data;
    } catch (error) {
      console.error('포스트 리스트 못 받는 중', error);
    }
  };
  const { data, fetchNextPage, hasNextPage, refetch } = useInfiniteQuery(
    ['hashtag'],
    ({ pageParam = 0 }) =>
      fetchPost({
        hashtag: tag,
        page: pageParam,
        size: 6,
        tab: tab,
      }),
    {
      //lastPage = 서버에서 받은 현재 페이지의 데이터
      getNextPageParam: (lastPage, allPosts) => {
        return lastPage && lastPage.page < lastPage.totalPage
          ? lastPage.page + 1
          : undefined;
      },
      staleTime: 60000,
      retry: false, // 1분주기 업데이트
    }
  );
  if (!data) return <div>데이터 가져오는중</div>;
  return (
    <>
      <div className={styles.tab}>
        <Nav variant="underline" className={styles.tabTitle}>
          <Nav.Item>
            <Nav.Link onClick={() => setTab(0)} active={tab === 0}>
              최신순
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              onClick={() => {
                setTab(1);
              }}
              active={tab === 1}
            >
              인기순
            </Nav.Link>
          </Nav.Item>
        </Nav>
        <div className={styles.hashtag}>#{tag}</div>
      </div>
      <div>
        <PostList
          data={data}
          fetchNextPage={fetchNextPage}
          hasNextPage={hasNextPage}
          navigate={navigate}
        />
      </div>
    </>
  );
}

export default SearchHashTagPage;
