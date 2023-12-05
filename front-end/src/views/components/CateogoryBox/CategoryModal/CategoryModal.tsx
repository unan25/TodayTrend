import React from "react";
import styles from "./CategoryModal.module.css";

type Props = {
  setModal: (value: boolean) => void;
};

const CategoryModal: React.FC<Props> = ({ setModal }) => {
  const onClickHandler = () => {
    setModal(false);
  };

  return (
    <div className={styles.modal_body}>
      <div className={styles.modal_header}>
        <button
          className={styles.modal_btn_close}
          type="button"
          onClick={onClickHandler}
        >
          X
        </button>
      </div>
    </div>
  );
};

export default CategoryModal;
