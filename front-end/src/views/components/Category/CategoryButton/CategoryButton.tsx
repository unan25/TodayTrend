import React from 'react';
import styles from './CategoryButton.module.css';

interface CategoryButtonProps {
  id: number;
  name: string;
  isSelected: boolean;
  onClick: (id: number) => void;
}
const CategoryButton: React.FC<CategoryButtonProps> = ({
  id,
  name,
  isSelected,
  onClick,
}) => {
  return (
    <>
      <button
        onClick={() => onClick(id)}
        className={`${styles.categoryButton} ${
          isSelected ? styles.selected : ''
        }`}
      >
        {name}
      </button>
    </>
  );
};

export default CategoryButton;
