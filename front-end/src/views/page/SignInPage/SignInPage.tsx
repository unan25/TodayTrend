// react
import React from "react";
import { useState, useEffect } from "react";

// react-router-dom
import { useNavigate } from "react-router-dom";

// redux
import { useDispatch, useSelector } from "react-redux";
import { signInUser } from "../../../redux/_actions/user_action";

// component
import { Form, FloatingLabel, Button } from "react-bootstrap";

// CSS
import styles from "./SignInPage.module.css";
import buttonStyle from "../../../module/styles/button.module.css";

// State
import { RootState } from "../../../redux/store";
import GoogleLogin from "../../../views/components/user/SocialLoginButton/Google/GoogleLogin";
import KakaoLogin from "../../../views/components/user/SocialLoginButton/Kakao/KakaoLogin";

function SignInPage() {
  // state & dispatch
  const dispatch = useDispatch<any>();
  const UUID = useSelector((state: RootState) => state.user.UUID);
  const role = useSelector((state: RootState) => state.user.role);

  // navigate
  const navigate = useNavigate();

  const [Email, SetEmail] = useState("");
  const [Password, SetPassword] = useState("");

  const emailHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    SetEmail(e.target.value);
  };

  const passwordHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    SetPassword(e.target.value);
  };

  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let account = {
      email: Email,
      password: Password,
    };

    const response = await dispatch(signInUser(account));
    console.log(response);
  };

  // signIn Success
  useEffect(() => {
    if (UUID && role === "GUEST") {
      navigate("/signup")
      return;
    };

    if (UUID && role) {
      navigate("/");
    }
  }, [UUID, role]);

  return (
    <div className="page-body">
      <form className={styles.mainForm} onSubmit={submitHandler}>
        <Form.Group className={styles.fg} controlId="SignUpForm">
          <FloatingLabel controlId="Email" label="이메일">
            <Form.Control
              type="email"
              placeholder="name@example.com"
              value={Email}
              onChange={emailHandler}
            />
          </FloatingLabel>
          <FloatingLabel controlId="Password" label="비밀번호">
            <Form.Control
              type="password"
              placeholder="Password"
              value={Password}
              onChange={passwordHandler}
            />
          </FloatingLabel>
        </Form.Group>
        <button className={buttonStyle.submitButton} type="submit">
          로그인
        </button>
      </form>
      <div className={styles.social_login}>
        <GoogleLogin />
        <KakaoLogin />
      </div>
    </div>
  );
}

export default SignInPage;
