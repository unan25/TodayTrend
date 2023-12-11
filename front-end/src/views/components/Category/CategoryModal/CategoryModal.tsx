import React from 'react';

import styles from './CategoryModal.module.css';

import { Modal, Button } from 'react-bootstrap';
import { CategoryType } from 'interface/CategoryInterface';

import axios from 'axios';
import { useQueryClient } from 'react-query';
import { IDetailPost } from 'views/page/LandingPage/LandingPage';

interface ModalProps {
  categories: CategoryType[];
  selectedCategories: number[];
  toggleCategory: (categoryId: number) => void;
  fetchPost: ({ page, size, categoryList, uuid, tab }: IDetailPost) => void;
  closeModal: () => void;
}

const CategoryModal: React.FC<ModalProps> = ({
  categories,
  selectedCategories,
  toggleCategory,
  closeModal,
  fetchPost,
}) => {
  const handleViewClick: any = () => {
    fetchPost({ page: 0, size: 6, categoryList: selectedCategories, tab:0 });
    closeModal();
  };

  return (
    <Modal show onHide={closeModal} centered className={styles.modal}>
      <Modal.Header closeButton>
        <Modal.Title>카테고리 선택</Modal.Title>
      </Modal.Header>
      <Modal.Body className={styles['modal-body-scroll']}>
        <div>
          {categories.map((category) => (
            <div key={category.id} className={styles.categoryItem}>
              <label htmlFor={category.name}>{category.name}</label>
              <input
                type="checkbox"
                id={category.name}
                checked={selectedCategories.includes(category.id)}
                onChange={() => toggleCategory(category.id)}
              />
            </div>
          ))}
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={handleViewClick}>스타일 보기</Button>
      </Modal.Footer>
    </Modal>
  );
};

export default CategoryModal;
