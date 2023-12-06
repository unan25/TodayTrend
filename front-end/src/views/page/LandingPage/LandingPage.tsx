// react
import React, { useEffect, useState } from 'react';

// react-query
import { useQuery } from 'react-query';

// react-bootstrap
import { Button, Nav } from 'react-bootstrap';

// styles
import styles from './LandingPage.module.css';

// components

import Modal from '../../components/Modal/CustomModal';


// type
import { CategoryList } from 'interface/CategoryList';

// axios
import axios from 'axios';

function LandingPage() {
  const 카테고리: CategoryList[] = [];

  const [categories, setCategories] = useState<CategoryList[]>([
    { id: 1, name: '남' },
    { id: 2, name: '여' },
    { id: 3, name: '미니멀' },
    { id: 4, name: '스트릿' },
    { id: 5, name: '이지캐주얼' },
    { id: 6, name: '비즈니스캐주얼' },
    { id: 7, name: '레트로' },
    { id: 8, name: '하이루' },
  ]);

  const [posts1, setPosts1] = useState<any[]>([
    {
      postId: 1,
      imageUrl: 'https://todaytrend.s3.ap-northeast-2.amazonaws.com/tt1.jpeg',
    },
    {
      postId: 2,
      imageUrl: 'https://todaytrend.s3.ap-northeast-2.amazonaws.com/tt1.jpeg',
    },
    {
      postId: 3,
      imageUrl: 'https://todaytrend.s3.ap-northeast-2.amazonaws.com/tt1.jpeg',
    },
  ]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get<CategoryList[]>(
          'api/post/categoryFilter'
        );
        setCategories(response.data);
      } catch (error) {
        console.log('카테고리 리스트 못 받는 중', error);
      }
    };
    fetchData();
  }, []);

  const mainCategories = categories.slice(0, 6);

  const [tab, setTab] = useState<number>(0);

  const [selectedCategories, setSelectedCategories] = useState<number[]>([]);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  useEffect(() => {
    console.log('Tab changed:', tab);
    console.log('Selected categories:', selectedCategories);
  }, [tab, selectedCategories]);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const toggleCategory = (categoryId: number) => {
    if (selectedCategories.includes(categoryId)) {
      setSelectedCategories(
        selectedCategories.filter((cat) => cat !== categoryId)
      );
    } else {
      setSelectedCategories([...selectedCategories, categoryId]);
    }
  };
  const handleMainCategoryClick = (categoryId: number) => {
    toggleCategory(categoryId);
  };

  const posts: any[] = [
    {
      id: 1,
      image: 'https://todaytrend.s3.ap-northeast-2.amazonaws.com/tt1.jpeg',
      category: '카테고리1',
    },
    {
      id: 2,
      image: 'https://todaytrend.s3.ap-northeast-2.amazonaws.com/tt2.png',
      category: '카테고리2',
    },
    // 추가 게시물 데이터
  ];

  return (
    <div>
      <div>
        <Nav variant="underline">
          <Nav.Item>
            <Nav.Link eventKey="0" onSelect={() => setTab(0)}>
              최신
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link onClick={() => setTab(1)}>좋아요</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link onClick={() => setTab(2)}>팔로잉</Nav.Link>
          </Nav.Item>
        </Nav>
      </div>

      <div>
        <button onClick={openModal}>
          <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADwAAAA8CAYAAAA6/NlyAAAACXBIWXMAACE4AAAhOAFFljFgAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAPTSURBVHgB7ZlNTttAFMefQ4RALFpuYJBYg3qChAO0dMOSKgu+NuXjAKXhAIVUQggQokWsWAR6AQIXqOgOIbW4awSCBQLx5f4fJNXETexxMo7H6vykUTz2aOzJe2/8/n5EBoPBYDAY4sKihFAqlejo6MhOpVID3LcsyxkbGzukkGi/4I2NDbq5ucm0tbXNua6b8Vw+QZsdHx/flZ0vRRrDi72/v/8Iq5ZqLJbpQSuura3NSU6pr4VXVlb45y1aUWK4+/j4ODg5ObkfNFBbC8OqbIxP4jlYeR8Ly+F3Gl1HuGTB5T9ITCtn4dXV1anyTVqNLRyfIFZ7Kx24u3V3d7cnuDpbuRdWdvwmTFMAy8vLGUy6SPHion0WT3R1dblnZ2eb8IRM5Rx27n6qtvw/aL1picB6F2L/9vaWXfwlhSTQwrwRwMp5/JNvGrlBM8BiduUQMTqC3y+Va9fX13zuvTgez/g7cE7SlBoxyjgcXmiXWOw7z7WqGK9HoIVjxH14eJgXYxTYsPoiGrtz1Vi0WZlJtY3hXC5HHR0dJcRuPmgsxszLZlvap5ZCAsLvZFu8xu9ltLxMwlEhMeJhe3ubTk9PB9LptM19WPUw6J1roAgtjFdZ01IuCpQvmDOzOlLOQZsJI+WiQOkuzTLNR8rZaDthpFwUKLMwdtMh/OzIjMWGkw2zs6pEpYUXxI6PlCN2eYoJlfLQFo4dxGqPZw6vqzukCGyKF2i7o6OjgUlKVPKw4D2BNPGrN00kRXCaidaPZz0ICpVIUkuvlCs/VCuUlhs0QMql8c/xh7TXfg8Nl+oRjvfRBjs7O9329na6urqyoF9/kWBVzHVCCsH9CgijQuA4UkStGEW/UJZyI41IuShQJg8Ro/kaUm6hhpRjpKRcFCiLYd4sylLOL474Q1s+zmxLeWpZTkDqSbn5uBKOCpGJh6WlJS2lXGL0MMMFtePj46fj7u5uGh4eprAkYsHlGpOFjZE3wr+SE+0Q3uNiT5CeS/vv0vz1EtXDLOJ/DwvkdznXmorof8diuT/Ef4gsWi94fX3dgmVZcu75SM4ij5FdtLYL5o93WMgQrOhVVi5Vv/osHsM1ZJJAKoa3traevvRTa+GF/KTqdJSzuU249gu0KXquDz8PtqxSX1/fYDab9Z+UAmBXQQVgGjeaotZjC8etqR6yqyCGFihe2IWrhMH/UD28FPuNVg+VycMo8JOc5+fnKfTFGHfRfxX0OTiJ1cOmJKfu1UNZycmdGZlJda8ehpGc30iCpFQP/SQnVw8PSJKkVg+xTvfHxMSEQwaDwWAwGBLDHxtjQaCfJOwKAAAAAElFTkSuQmCC"></img>
        </button>
        <span>선택된 카테고리 수: {selectedCategories.length}</span>
      </div>

      <div className={styles.mainCategoryContainer}>
        {mainCategories.map((category) => (
          <Button
            key={category.id}
            variant={
              selectedCategories.includes(category.id)
                ? 'primary'
                : 'outline-primary'
            }
            onClick={() => handleMainCategoryClick(category.id)}
            className={styles.mainCategoryButton}
          >
            {category.name}
          </Button>
        ))}
      </div>

      <div>
        {posts.map((post) => (
          <div key={post.id} className={styles.post}>
            <img src={post.image} alt={post.title} />
          </div>
        ))}
      </div>

      {isModalOpen && (
        <Modal
          categories={categories}
          selectedCategories={selectedCategories}
          toggleCategory={toggleCategory}
          closeModal={closeModal}
        />
      )}
    </div>
  );
}

export default LandingPage;
