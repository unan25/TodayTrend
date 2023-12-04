// react
import React, { useEffect, useState } from "react";

// react-query
import { useQuery } from "react-query";

// react-bootstrap
import { Container, Row, Col, Nav } from 'react-bootstrap';

// styles
import styles from "./LandingPage.module.css";

// components

import Modal from '../../components/Modal/CustomModal';


// type

// axios
import axios from "axios";

function LandingPage() {

  const categories: string[] = ['카테고리1', '카테고리2', '카테고리3'];
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const toggleCategory = (category: string) => {
    if (selectedCategories.includes(category)) {
      setSelectedCategories(
        selectedCategories.filter((cat) => cat !== category)
      );
    } else {
      setSelectedCategories([...selectedCategories, category]);
    }
  };

  const posts: any[] = [
    { id: 1, title: '게시물 1', image: '이미지 링크1', category: '카테고리1' },
    { id: 2, title: '게시물 2', image: '이미지 링크2', category: '카테고리2' },
    // 추가 게시물 데이터
  ];

  return (
    <div>
      <div>
        <Nav variant="underline">
          <Nav.Item>
            <Nav.Link>최신</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link>좋아요</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link>팔로잉</Nav.Link>
          </Nav.Item>
        </Nav>

        <button onClick={openModal}>카테고리 필터</button>
      </div>
      <div>
        {posts.map((post) => (
          <div key={post.id} className={styles.post}>
            <img src={post.image} alt={post.title} />
            <h3>{post.title}</h3>
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
