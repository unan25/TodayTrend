import React from "react";
import styles from "./SelectBox.module.css";

type Props = {
  options?: string[];
  onChange?: (value: string) => void;
};

const SelectBox: React.FC<Props> = ({ options, onChange }) => {
  const renderedOptions = options?.map((e, i) => (
    <option key={i} value={e}>
      {e}
    </option>
  ));

  return (
    <div>
      <select
        className={styles.selectBox}
        onChange={(e) => onChange?.(e.target.value)}
      >
        {renderedOptions}
      </select>
    </div>
  );
};

export default SelectBox;
