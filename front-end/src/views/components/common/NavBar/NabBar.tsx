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
      <div className={styles.section1}>로고</div>
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
            로그아웃
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
