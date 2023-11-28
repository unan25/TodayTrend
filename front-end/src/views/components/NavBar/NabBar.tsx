import React from "react";

import styles from "./NabBar.module.css";
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

import { logOut } from "../../../redux/_actions/user_action";
import { useDispatch } from "react-redux";

const NavBar: React.FC = () => {
  const dispatch = useDispatch<any>();
  const UUID = useSelector((state: RootState) => state.user.UUID);

  const onClickhandler = () => {
    const data = {
      uuid: UUID,
    };
    dispatch(logOut(data));
  };

  return (
    <nav className={styles.nav}>
      <div className={styles.section1}>로고</div>
      <div className={styles.section2}>
        <a href="">
          <div>마이페이지</div>
        </a>
        <a href="">
          <div>알림</div>
        </a>
        {!UUID ? (
          <>
            <a href="/create-account">
              <div>회원가입</div>
            </a>
            <a href="/signin">
              <div>로그인</div>
            </a>
          </>
        ) : (
          <button className={styles.button_logout} onClick={onClickhandler}>
            로그아웃
          </button>
        )}
      </div>
    </nav>
  );
};

export default NavBar;
