import React from 'react';
import CategoryButton from '../CategoryButton/CategoryButton';
import styles from './CategoryList.module.css';

interface CategoryListProps {
  categories: { id: number; name: string }[];
  selectedCategories: number[];
  onClick: (id: number) => void;
}

const CategoryList: React.FC<CategoryListProps> = ({
  categories,
  selectedCategories,
  onClick,
}) => (
  <div className={styles.mainCategoryContainer}>
    {categories.map((category) => (
      <CategoryButton
        key={category.id}
        id={category.id}
        name={category.name}
        isSelected={selectedCategories.includes(category.id)}
        onClick={onClick}
      />
    ))}
  </div>
);

export default CategoryList;
