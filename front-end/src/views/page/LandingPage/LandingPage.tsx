// react
import React, { useEffect, useState } from "react";

// react-query
import { useInfiniteQuery, useQuery, useQueryClient } from "react-query";
// react-bootstrap
import { Nav } from "react-bootstrap";
// styles
import styles from "./LandingPage.module.css";
// components
import CategoryModal from "../../components/Category/CategoryModal/CategoryModal";
import CategoryList from "../../components/Category/CategoryList/CategoryList";
import PostList from "../../components/post/PostList/PostList";
// type
import { CategoryType } from "interface/CategoryInterface";
// axios
import axios from "axios";
// router
import { useNavigate, useLocation } from "react-router-dom";
// redux
import { useSelector } from "react-redux";
import { RootState } from "redux/store";
// import { sessionStorage } from 'redux-persist/es/storage/session';

export interface IDetailPost {
  page: number;
  size: number;
  categoryList?: number[];
  uuid?: string;
  tab?: number;
}
function LandingPage() {
  const navigate = useNavigate();
  // const location = useLocation();
  const uuid = useSelector((state: RootState) => state.user.UUID);

  const [categories, setCategories] = useState<CategoryType[]>([]);
  const mainCategories = categories.slice(0, 6);
  const [tab, setTab] = useState<number>(0);
  const [categoryList, setCategoryList] = useState<number[]>([]); //카테고리 ID List
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  //세션스토리지에 카테고리 저장해놓기
  // const selectedCategoryList = sessionStorage.setItem('category', categoryList);

  //무한 스크롤
  const fetchPost = async ({
    page,
    size,
    categoryList,
    uuid,
    tab,
  }: IDetailPost) => {
    try {
      const requestBody = {
        page,
        size,
        categoryList,
        uuid,
        tab,
      };
      // axios.post를 사용하여 데이터를 body에 실어서 요청 보내기
      const response = await axios.post("/api/post/main", requestBody);
      refetch();
      return response.data;
    } catch (error) {
      console.error("포스트 리스트 못 받는 중", error);
    }
  };
  // 메인페이지 로딩 카테고리리스트 받아오기
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get<CategoryType[]>(
          "api/post/admincategorylist"
        );
        setCategories(response.data);
      } catch (error) {
        console.log("카테고리 리스트 못 받는 중", error);
      }
    };
    fetchData();
  }, []);
  // 탭or 카테고리 바뀌면 필터된 게시물 요청하기
  const toggleCategory = (categoryId: number) => {
    if (categoryList.includes(categoryId)) {
      setCategoryList(categoryList.filter((cat) => cat !== categoryId));
    } else {
      setCategoryList([...categoryList, categoryId]);
    }
  };
  const handleMainCategoryClick = (categoryId: number) => {
    toggleCategory(categoryId);
  };
  useEffect(() => {
    console.log("선택한 탭:" + tab);
    console.log("선택한 카테고리:" + categoryList);
    fetchPost({
      page: 0,
      size: 6,
      categoryList: categoryList,
      tab: tab,
      uuid: uuid,
    });
  }, [tab, categoryList]);

  const openModal = () => {
    setIsModalOpen(true);
  };
  const closeModal = () => {
    setIsModalOpen(false);
  };
  const { data, fetchNextPage, hasNextPage, refetch } = useInfiniteQuery(
    ["posts"],
    ({ pageParam = 0 }) =>
      fetchPost({
        page: pageParam,
        size: 6,
        categoryList: categoryList,
        uuid: uuid,
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
    <div>
      <div className={styles.nav}>
        <Nav variant="underline">
          <Nav.Item>
            <Nav.Link onClick={() => setTab(0)} active={tab === 0}>
              최신
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              onClick={() => {
                setTab(1), setCategoryList([]);
              }}
              active={tab === 1}
            >
              좋아요
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link
              onClick={() => {
                setTab(2), setCategoryList([]);
              }}
              active={tab === 2}
            >
              팔로잉
            </Nav.Link>
          </Nav.Item>
        </Nav>
      </div>
      {tab === 0 && (
        <>
          <div className={styles.mainCategoryContainer}>
            <button onClick={openModal} className={styles.categoryFilterButton}>
              <img
                className={styles.categoryFilter}
                src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADwAAAA8CAYAAAA6/NlyAAAACXBIWXMAACE4AAAhOAFFljFgAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAPTSURBVHgB7ZlNTttAFMefQ4RALFpuYJBYg3qChAO0dMOSKgu+NuXjAKXhAIVUQggQokWsWAR6AQIXqOgOIbW4awSCBQLx5f4fJNXETexxMo7H6vykUTz2aOzJe2/8/n5EBoPBYDAY4sKihFAqlejo6MhOpVID3LcsyxkbGzukkGi/4I2NDbq5ucm0tbXNua6b8Vw+QZsdHx/flZ0vRRrDi72/v/8Iq5ZqLJbpQSuura3NSU6pr4VXVlb45y1aUWK4+/j4ODg5ObkfNFBbC8OqbIxP4jlYeR8Ly+F3Gl1HuGTB5T9ITCtn4dXV1anyTVqNLRyfIFZ7Kx24u3V3d7cnuDpbuRdWdvwmTFMAy8vLGUy6SPHion0WT3R1dblnZ2eb8IRM5Rx27n6qtvw/aL1picB6F2L/9vaWXfwlhSTQwrwRwMp5/JNvGrlBM8BiduUQMTqC3y+Va9fX13zuvTgez/g7cE7SlBoxyjgcXmiXWOw7z7WqGK9HoIVjxH14eJgXYxTYsPoiGrtz1Vi0WZlJtY3hXC5HHR0dJcRuPmgsxszLZlvap5ZCAsLvZFu8xu9ltLxMwlEhMeJhe3ubTk9PB9LptM19WPUw6J1roAgtjFdZ01IuCpQvmDOzOlLOQZsJI+WiQOkuzTLNR8rZaDthpFwUKLMwdtMh/OzIjMWGkw2zs6pEpYUXxI6PlCN2eYoJlfLQFo4dxGqPZw6vqzukCGyKF2i7o6OjgUlKVPKw4D2BNPGrN00kRXCaidaPZz0ICpVIUkuvlCs/VCuUlhs0QMql8c/xh7TXfg8Nl+oRjvfRBjs7O9329na6urqyoF9/kWBVzHVCCsH9CgijQuA4UkStGEW/UJZyI41IuShQJg8Ro/kaUm6hhpRjpKRcFCiLYd4sylLOL474Q1s+zmxLeWpZTkDqSbn5uBKOCpGJh6WlJS2lXGL0MMMFtePj46fj7u5uGh4eprAkYsHlGpOFjZE3wr+SE+0Q3uNiT5CeS/vv0vz1EtXDLOJ/DwvkdznXmorof8diuT/Ef4gsWi94fX3dgmVZcu75SM4ij5FdtLYL5o93WMgQrOhVVi5Vv/osHsM1ZJJAKoa3traevvRTa+GF/KTqdJSzuU249gu0KXquDz8PtqxSX1/fYDab9Z+UAmBXQQVgGjeaotZjC8etqR6yqyCGFihe2IWrhMH/UD28FPuNVg+VycMo8JOc5+fnKfTFGHfRfxX0OTiJ1cOmJKfu1UNZycmdGZlJda8ehpGc30iCpFQP/SQnVw8PSJKkVg+xTvfHxMSEQwaDwWAwGBLDHxtjQaCfJOwKAAAAAElFTkSuQmCC"
              ></img>
              <span className={styles.categoryListLength}>
                {categoryList.length}
              </span>
            </button>
            <CategoryList
              categories={mainCategories}
              selectedCategories={categoryList}
              onClick={handleMainCategoryClick}
            />
          </div>
        </>
      )}
      {/* <PostList
        data={data}
        fetchNextPage={fetchNextPage}
        hasNextPage={hasNextPage}
        navigate={navigate}
      /> */}
      {isModalOpen && (
        <CategoryModal
          categories={categories}
          selectedCategories={categoryList}
          toggleCategory={toggleCategory}
          refetch={refetch}
          fetchPost={fetchPost}
          closeModal={closeModal}
        />
      )}
    </div>
  );
}

export default LandingPage;
