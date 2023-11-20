// react
import React, { useEffect, useState } from 'react';

// react-query
import { useQuery } from 'react-query';

// react-bootstrap
import { Container, Row, Col } from 'react-bootstrap';

// styles
import styles from './LandingPage.module.css';

// components
import SideBar from '../../components/Sidebar/SideBar';
import PostList from '../../components/PostList/PostList';
import Category from '../../../views/components/Category/Category';

// type
import { CategoryType } from 'interface/Categoryinterface';

// axios
import axios from 'axios';

function LandingPage() {
  // 카테고리리스트 받아오는 요청 get
  const { data: categoryList } = useQuery<CategoryType[]>(
    'categories',
    async () => {
      const response = await axios.get(
        'https://jsonplaceholder.typicode.com/users'
      );
      return response.data;
    }
  );
  // 카테고리 설정
  const [selectedCategories, setSelectedCategories] = useState<any>([]);

  // 포스트리스트 받아오는 요청
  const { data: postList } = useQuery(
    ['postList', selectedCategories],
    async () => {
      const response = await axios.post(
        'http://localhost:8080/api/image/test1',
        {
          selectedCategories,
        }
      );
      return console.log(response.data);
      // return response.data;
    }
  );

  return (
    <>
      <div className={styles.category}>
        {categoryList?.map((category: any) => (
          <Category
            key={category.id}
            category={category}
            selectedCategories={selectedCategories}
            setSelectedCategories={setSelectedCategories}
          ></Category>
        ))}
      </div>
      <div className={styles.pageBody}>
        <PostList postList={postList}></PostList>
      </div>
    </>
  );
}

export default LandingPage;
