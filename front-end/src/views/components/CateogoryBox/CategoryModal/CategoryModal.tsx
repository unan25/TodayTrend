import React, { useEffect, useState } from "react";
import styles from "./CategoryModal.module.css";
import axios from "axios";

type Props = {
  setModal: React.Dispatch<React.SetStateAction<boolean>>;
  setCategory: React.Dispatch<React.SetStateAction<Category[]>>;
  checked: Category[];
};

type Category = {
  id: number;
  name: string;
};

const CategoryModal: React.FC<Props> = ({ setModal, checked, setCategory }) => {
  const [categoryList, setCategoryList] = useState([]);

  const modalHandler = () => {
    setModal(false);
  };

  const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const key = parseInt(e.currentTarget.getAttribute("data-idvalue")!);
    const foundCategory = categoryList.find(
      (category: Category) => category.id === key
    );

    if (foundCategory) {
      if (checked.some((item) => item.id === key)) {
        setCategory((prev) => prev.filter((item) => item.id !== key));
      } else {
        setCategory((prev) => [...prev, foundCategory]);
      }
    }
  };

  const getCategoryList = async () => {
    try {
      const response = await axios.get("/api/post/admincategorylist");
      setCategoryList(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const renderCategory: React.FC<Category[]> = (categoryList: Category[]) => {
    return categoryList.map((e: Category, i: number) => {
      return (
        <label
          key={e.id}
          htmlFor={`i-${i}`}
          className={styles.modal_body__category_label}
        >
          <input
            type="checkbox"
            id={`i-${i}`}
            key={e.id}
            className={styles.modal_body__category_checkbox}
            value={e.name}
            data-idvalue={e.id}
            onChange={onChangeHandler}
            checked={checked.some((item) => item.id === e.id)}
          />
          {e.name}
        </label>
      );
    });
  };

  useEffect(() => {
    getCategoryList();
  }, []);

  return (
    <div className={styles.modal}>
      <div className={styles.modal_header}>
        <button
          className={styles.modal_btn_close}
          type="button"
          onClick={modalHandler}
        >
          X
        </button>
      </div>
      <div className={styles.modal_body}>{renderCategory(categoryList)}</div>
    </div>
  );
};

export default CategoryModal;
