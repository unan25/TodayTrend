import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { createAccount } from "../../../state/_actions/user_action";
import styles from "./CreateAccountPage.module.css";
import OnChangeInput from "../../components/OnChangeInput/OnChangeInput";
import AlertBox from "../../components/AlertBox/AlertBox";
import { useAccountValidation } from "../../../hook/useAccountValidation";

function AccountPage() {
  const dispatch = useDispatch<any>();
  const signUpSuccess = useSelector((state: any) => state.user.signUpSuccess);
  const navigate = useNavigate();

  const { Fields, handleChange, Message, IsValidated } = useAccountValidation();

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let account = {
      email: Fields.email,
      password: Fields.password,
    };

    dispatch(createAccount(account));
  };

  useEffect(() => {
    if (signUpSuccess) navigate("/");
  }, [signUpSuccess]);

  return (
    <div className={styles.body}>
      <form className={styles.mainForm} onSubmit={submitHandler}>
        <OnChangeInput
          type="email"
          placeholder="이메일"
          value={Fields.email}
          required={true}
          onChange={handleChange("email")}
        />
        <OnChangeInput
          type="password"
          placeholder="비밀번호"
          value={Fields.password}
          required={true}
          onChange={handleChange("password")}
        />
        <OnChangeInput
          type="password"
          placeholder="비밀번호 확인"
          value={Fields.confirmPassword}
          required={true}
          onChange={handleChange("confirmPassword")}
        />
        <AlertBox isError={true} message={Message} />
        <button
          type="submit"
          className={styles.submitButton}
          disabled={IsValidated}
        >
          회원가입
        </button>
      </form>
    </div>
  );
}

export default AccountPage;
