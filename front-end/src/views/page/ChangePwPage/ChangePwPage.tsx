// react
import React, { useEffect } from "react";
import { useState } from "react";

// react-router-dom
import { useLocation } from "react-router-dom";

//axios
import axios, { AxiosError } from "axios";

// redux
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

// CSS
import styles from "./ChangePwPage.module.css";

const ChangePassword: React.FC = () => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");

  // redux state에서 UUID 추출
  const uuid = useSelector((state: RootState) => state.user.UUID);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const data = {
      uuid: uuid,
      currentPassword,
      newPassword,
      confirmPassword,
    };

    // axios
    try {
      console.log(currentPassword);

      const response = await axios.put(`api/auth/change-password?uuid=${uuid}&currentPassword=${currentPassword}&newPassword=${newPassword}&confirmPassword=${confirmPassword}`);

      setMessage(response.data);
      console.log("비밀번호 변경 완료.");
    } catch (error) {
      const axiosError = error as AxiosError;
      console.error(error);
    }
  };

  //------------------------------------------------------------------------------
  return (
    <div className={styles.change_pw_top}>
        <div className={styles.change_pw_div}>
  <form className={styles.change_pw_form} onSubmit={handleSubmit}>
    <div>
      <label className={styles.change_pw_label}>현재 비밀번호</label>
      <input className={styles.change_pw_input}
        type="password"
        value={currentPassword}
        onChange={(e) => setCurrentPassword(e.target.value)}
      />
    </div>
    <div>
      <label className={styles.change_pw_label}>새로운 비밀번호</label>
      <input className={styles.change_pw_input}
        type="password"
        value={newPassword}
        onChange={(e) => setNewPassword(e.target.value)}
      />
    </div>
    <div>
      <label className={styles.change_pw_label}>새로운 비밀번호 확인</label>
      <input className={styles.change_pw_input}
        type="password"
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
      />
    </div>
    <input className={styles.change_pw_button} type="submit" value="비밀번호 변경" />
  </form>
  <p className={styles.change_pwp}>{message}</p>
</div>
    </div>

  );
};

export default ChangePassword;
