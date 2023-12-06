import React from 'react';

import styles from './Modal.module.css';

import { Modal, Button } from 'react-bootstrap';
import { CategoryList } from 'interface/CategoryList';

import axios from 'axios';
import { useQueryClient } from 'react-query';

interface ModalProps {
  categories: CategoryList[];
  selectedCategories: number[];
  toggleCategory: (categoryId: number) => void;
  closeModal: () => void;
}

const CustomModal: React.FC<ModalProps> = ({
  categories,
  selectedCategories,
  toggleCategory,
  closeModal,
}) => {
  const queryClient = useQueryClient();

  const handleViewClick: any = () => {
    console.log(selectedCategories);

    const fetchData = async () => {
      try {
        const response = await axios.post('api/post/카테고리필터보내기', {
          selectedCategories,
        });
        queryClient.setQueryData('postList', response.data);
      } catch (error) {
        console.log('오류', error);
      }
    };
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

export default CustomModal;
