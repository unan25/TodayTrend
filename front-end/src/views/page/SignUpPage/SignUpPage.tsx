// react
import React, { useEffect, useState, useCallback } from "react";

// axios
import axios from "axios";

// react-router-dom
import { useNavigate } from "react-router-dom";

// redux & action
import { useDispatch, useSelector } from "react-redux";
import { signUpUser } from "../../../state/_actions/user_action";

// react-dropzone
import { useDropzone } from "react-dropzone";

// style
import styles from "./SignUpPage.module.css";

// component
import {
  Form,
  FloatingLabel,
  Button,
  Alert,
  Row,
  Image,
  FormControlProps,
} from "react-bootstrap";
import Input from "../../components/Input/Input";

function SignUpPage() {
  // dispatch & state
  const dispatch = useDispatch<any>();

  const signUpSuccess = useSelector((state: any) => state.user.signUpSuccess);

  // navigate
  const navigate = useNavigate();

  // form
  const [UploadedFile, SetUploadedFile] = useState("");
  const [Email, SetEmail] = useState("");
  const [Password, SetPassword] = useState("");
  const [Name, SetName] = useState("");
  const [Lastname, SetLastname] = useState("");

  // duplication
  const [Duplication, SetDuplication] = useState(false);
  const [Message, SetMessage] = useState("");

  //------------------------------------------------------------------------------
  // check duplication

  type Email = string;

  function checkDuplication(email: Email) {
    const emailObj = {
      email: email,
    };

    axios.post("/api/users/check-duplication", emailObj).then((res) => {
      SetDuplication(res.data.isDuplicated);
      SetMessage(res.data.message);
    });
  }

  // debouncing
  function debounce(func: Function, delay: any) {
    let timer: NodeJS.Timeout;
    return function (...args: any) {
      clearTimeout(timer);

      timer = setTimeout(() => {
        func(...args);
      }, delay);
    };
  }

  const emailHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newEmail = e.target.value;

    SetEmail(newEmail);

    // Currying (method chaining)
    debounce(checkDuplication, 1000)(newEmail);
  };
  //------------------------------------------------------------------------------

  // event handler
  const passwordHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    SetPassword(e.target.value);
  };

  const nameHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    SetName(e.target.value);
  };

  const lastnameHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    SetLastname(e.target.value);
  };

  // sign-up
  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let userInfo = {
      email: Email,
      password: Password,
      lastname: Lastname,
      name: Name,
    };

    dispatch(signUpUser(userInfo));
  };
  //------------------------------------------------------------------------------\
  // dropzone config
  const onDrop = useCallback((acceptedFiles: any) => {
    SetUploadedFile(acceptedFiles);
  }, []);

  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  //------------------------------------------------------------------------------\
  // state control
  useEffect(() => {
    if (signUpSuccess) navigate("/");
  }, [signUpSuccess]);

  //------------------------------------------------------------------------------

  function DuplicationAlert(isDuplicated: boolean, message: string) {
    const style = isDuplicated ? styles.red : styles.green;

    if (message !== "") {
      return (
        <>
          <Alert className={`${styles.alertBox} ${style}`} variant="message">
            {message}
          </Alert>
        </>
      );
    }
  }

  //------------------------------------------------------------------------------
  // rendering component
  function ProfileImageUpload() {
    return (
      <div className={styles.dropzone}>
        <Row className={styles.dropzone_row1}>
          <Image src="#" alt="" className={styles.dropzone__img} />
          <div {...getRootProps()} className={styles.dropzone__drop}>
            <input {...getInputProps()} />
          </div>
        </Row>
        <Row>
          <input type="file" />
        </Row>
      </div>
    );
  }

  //------------------------------------------------------------------------------

  return (
    <Form className={styles.mainForm} onSubmit={submitHandler}>
      <form>
        <Input placeholder="EMAIL" />
        <Input placeholder="PW" />
      </form>
      <Form.Group className={styles.fg} controlId="SignUpForm">
        <FloatingLabel controlId="Email" label="이메일">
          {DuplicationAlert(Duplication, Message)}
          <Form.Control
            type="email"
            placeholder="name@example.com"
            value={Email}
            onChange={emailHandler}
            required
          />
        </FloatingLabel>
        <FloatingLabel controlId="Password" label="비밀번호">
          <Form.Control
            type="password"
            placeholder="Password"
            value={Password}
            onChange={passwordHandler}
            required
            minLength={5}
          />
        </FloatingLabel>

        {ProfileImageUpload()}

        <FloatingLabel controlId="Lastname" label="성">
          <Form.Control
            type="text"
            placeholder="Lastname"
            value={Lastname}
            onChange={lastnameHandler}
            required
          />
        </FloatingLabel>
        <FloatingLabel controlId="Name" label="이름">
          <Form.Control
            type="text"
            placeholder="Name"
            value={Name}
            onChange={nameHandler}
            required
          />
        </FloatingLabel>
      </Form.Group>
      <Button className={styles.submitButton} variant="primary" type="submit">
        회원 가입
      </Button>
    </Form>
  );
}

export default SignUpPage;
