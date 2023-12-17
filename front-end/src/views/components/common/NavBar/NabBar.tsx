import React, { useEffect, useState } from "react";

//
import { Link } from "react-router-dom";

//
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { RootState } from "redux/store";

//
import { logOut } from "../../../../redux/_actions/user_action";

//
import styles from "./NabBar.module.css";
import axios from "axios";

const NavBar: React.FC = () => {
  const dispatch = useDispatch<any>();
  //
  const UUID = useSelector((state: RootState) => state.user.UUID);
  const role = useSelector((state: RootState) => state.user.role);

  const [nickname, setNickname] = useState<string>();
  const [profileImage, setProfileImage] = useState<string>();

  const onClickhandler = () => {
    const data = {
      uuid: UUID,
    };
    dispatch(logOut(data));
  };

  const getNickname = async () => {
    try {
      const response = await axios.get(`/api/users/uuid/${UUID}`);
      setNickname(response.data.nickname);
      setProfileImage(response.data.profileImage);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (UUID) getNickname();
  }, [UUID]);

  return (
    <nav className={styles.nav}>
      <div className={styles.section1}>
        <img
          className={styles.navBar_logo}
          src="https://todaytrend.s3.ap-northeast-2.amazonaws.com/5/84c99ce9-5079-41cd-a4c2-6cafffcb6c6cTodayTrendSmallLogo2.png"
        />
      </div>
      <img
        className={styles.navBar_title}
        src="https://todaytrend.s3.ap-northeast-2.amazonaws.com/7/d3b700f5-201c-4c7a-a907-5299716e7aeeTodayTrendLogoTitle2.png.jpg"
      />
      {!UUID && (
        <div className={styles.section2}>
          <Link className={styles.section2__signUpButton} to="/signup">
            회원가입
          </Link>
          <Link className={styles.section2__signInButton} to="/signin">
            로그인
          </Link>
        </div>
      )}
      {role === "GUEST" && (
        <div className={styles.section2}>
          <Link className={styles.section2__signUpButton} to="/signup">
            회원가입
          </Link>
          <Link className={styles.section2__signInButton} to="/signin">
            로그인
          </Link>
          <button className={styles.button_logout} onClick={onClickhandler}>
            게스트 로그아웃
          </button>
        </div>
      )}

      {role === "USER" && (
        <div className={styles.section2}>
          <Link
            className={styles.nav_section_profile}
            to={`/profile/${nickname}`}
          >
            <img
              className={styles.nav_section_profileImage}
              src={profileImage}
            />
            <span>{nickname}</span>
          </Link>
          <div className={styles.nav_section2__notification}>알림</div>
          <button className={styles.button_logout} onClick={onClickhandler}>
            로그아웃
          </button>
        </div>
      )}
    </nav>
  );
};

export default NavBar;
