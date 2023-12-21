import React, { useEffect, useState } from "react";

import styles from "./CategoryBox.module.css";

import CategoryModal from "./CategoryModal/CategoryModal";

type Category = {
  id: number;
  name: string;
};

type Props = {
  setCategory: React.Dispatch<React.SetStateAction<number[]>>;
};

const CategoryBox: React.FC<Props> = ({ setCategory }) => {
  const [modal, setModal] = useState(false);
  const [checkedCategory, setCheckedCategory] = useState<Category[]>([]);

  const onClickHandler = () => {
    setModal((prev) => !prev);
  };

  const renderCheckedCategory = () => {
    return checkedCategory.map((e, i) => {
      return (
        <div className={styles.categorybox_category} key={i}>
          {e.name}
        </div>
      );
    });
  };

  useEffect(() => {
    setCategory([...checkedCategory.map((e) => e.id)]);
  }, [checkedCategory]);

  return (
    <div className={styles.componenet_body}>
      <div className={styles.categorybox}>{renderCheckedCategory()}</div>
      <button
        type="button"
        className={styles.btn_modal}
        onClick={onClickHandler}
      >
        +
      </button>
      {modal && (
        <CategoryModal
          setModal={setModal}
          checked={checkedCategory}
          setCategory={setCheckedCategory}
        />
      )}
    </div>
  );
};

export default CategoryBox;
