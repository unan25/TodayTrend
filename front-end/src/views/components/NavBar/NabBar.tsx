import React from "react";

import styles from "./NabBar.module.css";

const NavBar: React.FC = () => {
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
        <a href="/create-account">
          <div>회원가입</div>
        </a>
        <a href="/signin">
          <div>로그인/로그아웃</div>
        </a>
      </div>
    </nav>
  );
};

export default NavBar;
