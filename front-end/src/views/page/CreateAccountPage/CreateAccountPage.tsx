// react
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

// redux
import { useDispatch, useSelector } from "react-redux";
import { createAccount } from "../../../redux/_actions/user_action";

// component
import OnChangeInput from "../../components/OnChangeInput/OnChangeInput";
import AlertBox from "../../components/AlertBox/AlertBox";

// custom hook
import { useAccountValidation } from "../../../hook/useAccountValidation";

// State
import { RootState } from "redux/store";

// style
import styles from "./CreateAccountPage.module.css";
import buttonStyle from "../../../module/styles/button.module.css";
import formStyle from "../../../module/styles/form.module.css";

function AccountPage() {
  const UUID = useSelector((state: RootState) => state.user.UUID_temp);
  const dispatch = useDispatch<any>();
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
    if (UUID) navigate("/create-userinfo");
  }, [UUID]);

  return (
    <div className="page-body">
      <form className={formStyle.mainForm} onSubmit={submitHandler}>
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
          className={buttonStyle.submitButton}
          disabled={!IsValidated}
        >
          회원가입
        </button>
      </form>
    </div>
  );
}

export default AccountPage;
