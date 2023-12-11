import React, { useEffect, useState } from "react";
//
import { useSelector } from "react-redux";
import { RootState } from "redux/store";
//
import { Link, useParams } from "react-router-dom";
//
import axios from "axios";
//
import { UserInfo } from "interface/UserInterface";
//
import FollowButton from "../../../views/components/user/FollowButton/FollowButton";
//
import styles from "./ProfilePage.module.css";
import FollowListModal from "../../../views/components/user/FollowListModal/FollowListModal";

type FollowCount = {
  follower: number;
  following: number;
};

type ListModal = {
  follower: boolean;
  following: boolean;
};

const ProfilePage: React.FC = () => {
  const me: string = useSelector((state: RootState) => state.user.UUID);

  // state
  const [userInfo, setUserInfo] = useState<UserInfo>({});

  const [followCount, seFollowCount] = useState<FollowCount>({
    follower: 0,
    following: 0,
  });

  const [modal, setModal] = useState<ListModal>({
    follower: false,
    following: false,
  });

  // 대상 유저
  const { uuid } = useParams();

  // axios
  const getUserInfo = async () => {
    try {
      const response = await axios.get(`/api/users/profile/${uuid}`);
      setUserInfo(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getFollowCount = async () => {
    try {
      const followCount = (await axios.get(`/api/users/follower-count/${uuid}`))
        .data;
      const followingCount = (
        await axios.get(`/api/users/following-count/${uuid}`)
      ).data;

      seFollowCount({ follower: followCount, following: followingCount });
    } catch (err) {
      console.error(err);
    }
  };

  // useEffect
  useEffect(() => {
    getFollowCount();
    getUserInfo();
  }, [uuid]);

  return (
    <div className="page-body">
      <div className={styles.profile_header}>
        <div>{userInfo.nickname}</div>
        <div>메뉴</div>
      </div>
      <div className={styles.profile_body}>
        <div className={styles.profile_section1}>
          <img
            src={userInfo.profileImage}
            className={styles.profile_section1__image}
            alt="프로필 이미지"
          />
          <div className={styles.profile_section1__infoBox}>
            <div className={styles.profile_section1__infoBox__name}>
              name: {userInfo.name}
            </div>
            <div className={styles.profile_section1__infoBox__link}>
              link: {userInfo.website}
            </div>
            <div className={styles.profile_section1__infoBox__introduction}>
              {userInfo.introduction}
            </div>
          </div>
        </div>
        <div className={styles.profile_section2}>
          <div className={styles.profile_section2__countBox}>
            <div className={styles.profile_section2__countBox__postCount}>
              <div>게시물</div>
              <Link to="">?</Link>
            </div>
            <div className={styles.profile_section2__countBox__followCount}>
              <div>팔로워</div>
              <Link
                onClick={(e) =>
                  setModal({
                    follower: true,
                    following: false,
                  })
                }
                to=""
              >
                {followCount.follower}
              </Link>
            </div>
            <div className={styles.profile_section2__countBox__followingCount}>
              <div>팔로잉</div>
              <Link
                onClick={(e) =>
                  setModal({
                    follower: false,
                    following: true,
                  })
                }
                to=""
              >
                {followCount.following}
              </Link>
            </div>
          </div>
          <div className={styles.profile_section2__buttonBox}>
            {me === uuid ? (
              <Link
                className={styles.profile_section2__buttonBox__button1}
                to={"/"}
              >
                수정
              </Link>
            ) : (
              <FollowButton from={me} to={uuid!} updataCount={getFollowCount} />
            )}
            {me === uuid ? (
              <button
                type="button"
                className={styles.profile_section2__buttonBox__button2}
              >
                공유
              </button>
            ) : (
              <button
                type="button"
                className={styles.profile_section2__buttonBox__button2}
              >
                DM
              </button>
            )}
          </div>
        </div>
        {modal.follower && (
          <FollowListModal
            updataCount={getFollowCount}
            modalType="follower"
            setModal={setModal}
          />
        )}
        {modal.following && (
          <FollowListModal
            updataCount={getFollowCount}
            modalType="following"
            setModal={setModal}
          />
        )}
      </div>
      <div className={styles.profile_postList}>바디</div>
    </div>
  );
};

export default ProfilePage;
