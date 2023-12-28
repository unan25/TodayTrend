import React, { ReactNode, useEffect, useState } from "react";
//
import axios from "axios";

//
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

//
import styles from "./Reccomendation.module.css";
import PostImage from "../PostImage/PostImage";
import { Link } from "react-router-dom";

type Props = {
  postId: number;
};

type SimplePost = {
  postId: number;
  imageUrl: string;
};

type Category = {
  id: number;
  name: string;
};

type ReccomendationByUser = {
  title: string;
  postList: SimplePost[];
};

type ReccomendationByCategory = {
  title: string;
  postList: SimplePost[];
};

type ReccomendationResponse = {
  title1: string;
  title2: string;
  postList1: SimplePost[];
  postList2: SimplePost[];
  categoryList: Category[];
  postUuid: string;
};

const Reccomendation: React.FC<Props> = ({ postId }) => {
  const UUID = useSelector((state: RootState) => state.user.UUID);

  const [reccomendationByUser, setReccomendationByUser] = useState<
    ReccomendationByUser | undefined
  >();

  const [reccomendationByCategory, setReccomendationByCategory] = useState<
    ReccomendationByCategory | undefined
  >();

  const getReccomendation = async () => {
    try {
      const response = await axios.post("/api/post/posts/detaillist", null, {
        params: {
          postId: postId,
        },
      });

      const reccomendation: ReccomendationResponse = response.data;

      setReccomendationByUser({
        title: reccomendation.title1,
        postList: reccomendation.postList1,
      });

      setReccomendationByCategory({
        title: reccomendation.title2,
        postList: reccomendation.postList2,
      });
    } catch (err) {
      console.error(err);
    }
  };

  const renderReccomendationByUser = () => {
    const nodes: ReactNode[] = [];

    reccomendationByUser?.postList.map((e) => {
      nodes.push(
        <Link
          className={styles.reccomendation_link}
          key={e.postId}
          to={`/post/${e.postId}`}
        >
          <PostImage imageUrl={e.imageUrl} postId={e.postId} />
        </Link>
      );
    });

    return nodes;
  };

  const renderReccomendationByCategory = () => {
    const nodes: ReactNode[] = [];

    reccomendationByCategory?.postList.map((e) => {
      nodes.push(
        <Link
          className={styles.reccomendation_link}
          key={e.postId}
          to={`/post/${e.postId}`}
        >
          <PostImage imageUrl={e.imageUrl} postId={e.postId} />
        </Link>
      );
    });

    return nodes;
  };

  useEffect(() => {
    if (postId) getReccomendation();
  }, [postId]);

  return (
    <div className={styles.component_body}>
      <div className={styles.reccomendation_body}>
        <div className={styles.reccomendation_body__header_user}>
          {reccomendationByUser?.title}
        </div>
        <div className={styles.reccomendation_body__section_user}>
          {renderReccomendationByUser()}
        </div>
        <div className={styles.reccomendation_body__header_category}>
          {reccomendationByCategory?.title}
        </div>
        <div className={styles.reccomendation_body__section_category}>
          {renderReccomendationByCategory()}
        </div>
      </div>
    </div>
  );
};

export default Reccomendation;
