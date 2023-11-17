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

// State
import { RootState } from "redux/store";

function SignInPage() {
  // state & dispatch
  const dispatch = useDispatch<any>();

  const signInSuccess = useSelector(
    (state: RootState) => state.user.signInSuccess
  );

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

  const submitHandler = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let account = {
      email: Email,
      password: Password,
    };

    dispatch(signInUser(account));
  };

  // signIn Success
  useEffect(() => {
    if (signInSuccess) navigate("/");
  }, [signInSuccess]);

  return (
    <Form className={styles.mainForm} onSubmit={submitHandler}>
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
      <Button className={styles.submitButton} variant="primary" type="submit">
        로그인
      </Button>
    </Form>
  );
}

export default SignInPage;
