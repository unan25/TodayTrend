import React, { useState, useEffect } from 'react';
import styles from './Category.module.css';
import axios from 'axios';

const Category: React.FC<any> = ({
  category,
  selectedCategories,
  setSelectedCategories,
}) => {
  // 카테고리 클릭하면 그 카테고리ID로 변경

  const handleCategoryClick = () => {
    // 선택된 카테고리의 ID를 배열에 추가하거나 제거
    if (selectedCategories.includes(category.id)) {
      // 이미 선택된 경우, 선택 해제
      let copy: any[] = [...selectedCategories];
      setSelectedCategories(copy.filter((id: any) => id !== category.id));
    } else {
      // 선택되지 않은 경우, 선택
      setSelectedCategories((prevSelected: any) => [
        ...prevSelected,
        category.id,
      ]);
    }
  };

  return (
    <div>
      <button
        className={`${styles.button} ${
          selectedCategories.includes(category.id) ? styles.selected : ''
        }`}
        onClick={handleCategoryClick}
      >
        {`${category.username}`}
      </button>
    </div>
  );
};
export default Category;
