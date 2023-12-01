// react
import React, { useState } from "react";

// component
import OnChangeInput from "../OnChangeInput/OnChangeInput";
import AlertBox from "../AlertBox/AlertBox";

// style
import formStyle from "../../../module/styles/form.module.css";
import styles from "./AccountForm.module.css";

// type
import { Account } from "interface/UserInterface";

// img
import show from "../../../images/input/show.png";
import hide from "../../../images/input/hide.png";

type Props = {
  fields: Account;
  message: string;
  handleChange: (field: string) => void;
};

const AccountForm: React.FC<Props> = ({ fields, message, handleChange }) => {
  const [toggle, setToggle] = useState(true);

  const onClickHandler = (e: React.MouseEvent<HTMLImageElement>) => {
    setToggle((prev) => !prev);
  };

  return (
    <div className={formStyle.signup_form}>
      <OnChangeInput
        type="email"
        placeholder="이메일"
        value={fields.email}
        required={true}
        onChange={handleChange("email")!}
      />
      <div className={styles.password_container}>
        <img
          className={styles.passwordToggle}
          src={toggle ? show : hide}
          onClick={onClickHandler}
        />
        <OnChangeInput
          type={toggle ? "password" : "text"}
          placeholder="비밀번호"
          value={fields.password}
          required={true}
          onChange={handleChange("password")!}
        />
      </div>

      <OnChangeInput
        type="password"
        placeholder="비밀번호 확인"
        value={fields.confirmPassword}
        required={true}
        onChange={handleChange("confirmPassword")!}
      />
      <AlertBox isError={true} message={message} />
    </div>
  );
};

export default AccountForm;
