import React, { useState } from "react";

import axios from "axios";
import styles from "./FindPwPage.module.css";
import buttonStyle from "../../../module/styles/button.module.css";
import { useSelector } from "react-redux";
import { RootState } from "redux/store";
import OnChangeInput from "../../../views/components/common/OnChangeInput/OnChangeInput";
import { useNavigate } from "react-router-dom";

function FindPwpage() {
  const navigate = useNavigate();

  const [email, setEmail] = useState<string>();

  const onClickHandler = async () => {
    try {
      const response = await axios.post("/api/auth/findPassword", null, {
        params: {
          userEmail: email,
        },
      });

      if (response.status === 200) {
        alert("임시 비밀번호가 발급되었습니다. 이메일을 확인해주세요^^");
        navigate("/");
      }
    } catch (err: any) {
      alert(
        "등록되지 않은 이메일입니다. 소셜 로그인 회원이라면 해당 플랫폼 계정을 확인해주세요"
      );
    }
  };

  return (
    <div className="page-body">
      <div className={styles.page_content}>
        <div className={styles.page_header}>비밀번호 찾기</div>
        <div className={styles.page_body}>
          <div className={styles.page_body__label}>이메일 입력</div>
          <OnChangeInput
            value={email}
            onChange={setEmail}
            type="email"
            placeholder="이메일을 입력해 주세요"
          />
          <div className={styles.page_footer}>
            <button
              onClick={onClickHandler}
              className={buttonStyle.submitButton}
            >
              임시 비밀번호 전송
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default FindPwpage;
