import React from "react";

import styles from "./Input.modules.css";

type Props = {
  type?: string;
  placeholder?: string;
};

const Input: React.FC<Props> = (props) => {
  return (
    <input
      className={styles.input}
      type={props.type}
      placeholder={props.placeholder}
    />
  );
};

export default Input;
