import React from "react";

import styles from "./OnChangeInput.modules.css";

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

  return (
    <input
      value={value}
      className={styles.input}
      type={type}
      placeholder={placeholder}
      onChange={changeEventHandler}
      required={required}
    />
  );
};

export default OnChangeInput;
