import React from "react";
import styles from "./AlertBox.module.css";

type Props = {
  isError?: boolean;
  message: string;
};

const AlertBox: React.FC<Props> = ({ isError, message }) => {
  const style = isError ? styles.red : styles.green;

  if (message !== "") {
    return <div className={`${styles.alertBox} ${style}`}>{message}</div>;
  }
};

export default AlertBox;
