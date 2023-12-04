import React from 'react';

import styles from './Modal.module.css';

import { Modal, Button } from 'react-bootstrap';

interface ModalProps {
  categories: string[];
  selectedCategories: string[];
  toggleCategory: (category: string) => void;
  closeModal: () => void;
}

const CustomModal: React.FC<ModalProps> = ({
  categories,
  selectedCategories,
  toggleCategory,
  closeModal,
}) => {
  return (
    <Modal show onHide={closeModal} centered className={styles.modal}>
      <Modal.Header closeButton>
        <Modal.Title>카테고리 선택</Modal.Title>
      </Modal.Header>
      <Modal.Body className={styles['modal-body-scroll']}>
        <div>
          {categories.map((category) => (
            <div key={category} className={styles.categoryItem}>
              <label htmlFor={category}>{category}</label>
              <input
                type="checkbox"
                id={category}
                checked={selectedCategories.includes(category)}
                onChange={() => toggleCategory(category)}
              />
            </div>
          ))}
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button>스타일 보기</Button>
      </Modal.Footer>
    </Modal>
  );
};

export default CustomModal;
