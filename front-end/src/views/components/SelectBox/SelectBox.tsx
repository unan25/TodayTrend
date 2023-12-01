import React from "react";
import styles from "./SelectBox.module.css";

type Props = {
  options?: string[];
  value?: string | number | readonly string[] | undefined;
  onChange?: (value: string) => void;
  required?: boolean;
};

const SelectBox: React.FC<Props> = ({ options, onChange, required, value }) => {
  const renderedOptions = options?.map((e, i) => {
    let gender;
    if (e === "MALE") gender = "남성";
    if (e === "FEMALE") gender = "여성";
    return (
      <option key={i} value={e}>
        {gender}
      </option>
    );
  });

  return (
    <div>
      <select
        className={styles.selectBox}
        onChange={(e) => onChange?.(e.target.value)}
        required={required}
        value={value}
      >
        {renderedOptions}
      </select>
    </div>
  );
};

export default SelectBox;
