import React, { useState } from "react";

import styles from "./OnChangeInput.modules.css";

import show from "../../../../images/input/show.png";
import hide from "../../../../images/input/hide.png";

type Props = {
  type?: string;
  value?: string;
  placeholder?: string;
  required?: boolean;
  onChange?: (value: string) => void;
};

const OnChangeInput: React.FC<Props> = ({
  type,
  value,
  placeholder,
  required,
  onChange,
}) => {
  const changeEventHandler: React.ChangeEventHandler<HTMLInputElement> = (
    e
  ) => {
    const { value } = e.target;
    onChange?.(value);
  };
  const [toggle, setToggle] = useState(true);

  const onClickHandler = (e: React.MouseEvent<HTMLImageElement>) => {
    setToggle((prev) => !prev);
  };

  return (
    <div className={styles.input_box}>
      <input
        value={value}
        className={styles.input}
        type={type}
        placeholder={placeholder}
        onChange={changeEventHandler}
        required={required}
      />
      {type === "password" && (
        <img
          className={styles.passwordToggle}
          src={toggle ? show : hide}
          onClick={onClickHandler}
        />
      )}
    </div>
  );
};

export default OnChangeInput;
