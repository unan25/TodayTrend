import React, { useState } from "react";

import styles from "./CategoryBox.module.css";

import CategoryModal from "./CategoryModal/CategoryModal";

const CategoryBox = () => {
  const [modal, setModal] = useState(false);

  const onClickHandler = () => {
    setModal((prev) => !prev);
  };

  return (
    <div className={styles.componenet_body}>
      <div className={styles.categorybox}>카테고리 박스</div>
      <button
        type="button"
        className={styles.btn_modal}
        onClick={onClickHandler}
      >
        +
      </button>
      {modal && <CategoryModal setModal={setModal} />}
    </div>
  );
};

export default CategoryBox;
