import React, { useEffect, useState } from 'react';
//
import { useSelector } from 'react-redux';
import { RootState } from 'redux/store';
//
import { Link, useNavigate, useParams } from 'react-router-dom';
//
import axios from '../../../setUpAxios';
//
import { UserInfo } from 'interface/UserInterface';
//
import FollowButton from '../../../views/components/user/FollowButton/FollowButton';
//
import styles from './ProfilePage.module.css';
import FollowListModal from '../../../views/components/user/FollowListModal/FollowListModal';
//
import { useInfiniteQuery } from 'react-query';
import PostList from '../../components/post/PostList/PostList';

type FollowCount = {
  follower: number;
  following: number;
};

type ListModal = {
  follower: boolean;
  following: boolean;
};

type Posts = {
  UUID: string;
  page: number;
  size: number;
};
const ProfilePage: React.FC = () => {
  // hook
  const navigate = useNavigate();

  // 현재 유저
  const me: string = useSelector((state: RootState) => state.user.UUID);

  // 대상 유저 UUID
  const [UUID, setUUID] = useState<string>('');

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
  const [postCount, setPostCount] = useState<number>(0);

  // 대상 유저
  const { nickname } = useParams();

  // axios
  const getUUID = async () => {
    try {
      const response = await axios.get(`/api/users/nickname/${nickname}`);
      const uuid = response.data[0].uuid;
      setUUID(response.data[0].uuid);

      return uuid;
    } catch (err) {
      console.error(err);
    }
  };

  const getUserInfo = async () => {
    try {
      const response = await axios.get(`/api/users/profile/${nickname}`);
      setUserInfo(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getFollowCount = async () => {
    try {
      const UUID = await getUUID();

      const followCount = await axios.get(`/api/users/follower-count/${UUID}`);

      const followingCount = await axios.get(
        `/api/users/following-count/${UUID}`
      );

      seFollowCount({
        follower: followCount.data,
        following: followingCount.data,
      });
    } catch (err) {
      console.error(err);
    }
  };

  const getPostCount = async () => {
    try {
      const response = await axios.get(`/api/post/user-cnt?uuid=${UUID}`);
      setPostCount(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  // infinite scroll
  const fetchUserPost = async ({ page, size, UUID }: Posts) => {
    try {
      if (UUID) {
        const response = await axios.get(
          `/api/post/user?uuid=${UUID}&page=${page}&size=${size}`
        );
        refetch();
        return response.data;
      }
    } catch (error) {}
  };

  const { data, fetchNextPage, hasNextPage, refetch } = useInfiniteQuery(
    ['userPost'],
    ({ pageParam = 0 }) =>
      fetchUserPost({
        page: pageParam,
        size: 4,
        UUID: UUID,
      }),
    {
      getNextPageParam: (lastPage) => {
        return lastPage && lastPage.page < lastPage.totalPage
          ? lastPage.page + 1
          : undefined;
      },
      staleTime: 60000,
      retry: false, // 1분주기 업데이트
    }
  );

  // useEffect
  useEffect(() => {
    getFollowCount();
    getUserInfo();
  }, [nickname]);

  useEffect(() => {
    fetchUserPost({ page: 0, size: 4, UUID: UUID });
    getPostCount();
  }, [UUID]);

  return (
    <div className="page-body">
      <div className={styles.profile_header}>
        <div>{userInfo.nickname}</div>
        <div></div>
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
              <Link to="">{postCount}</Link>
            </div>
            <div className={styles.profile_section2__countBox__followCount}>
              <div>팔로워</div>
              <button
                type="button"
                onClick={(e) =>
                  setModal({
                    follower: true,
                    following: false,
                  })
                }
              >
                {followCount.follower}
              </button>
            </div>
            <div className={styles.profile_section2__countBox__followingCount}>
              <div>팔로잉</div>
              <button
                type="button"
                onClick={(e) =>
                  setModal({
                    follower: false,
                    following: true,
                  })
                }
              >
                {followCount.following}
              </button>
            </div>
          </div>
          <div className={styles.profile_section2__buttonBox}>
            {me === UUID ? (
              <Link
                className={styles.profile_section2__buttonBox__button1}
                to={`/update-userinfo/${nickname}`}
              >
                수정
              </Link>
            ) : (
              <FollowButton from={me} to={UUID!} updataCount={getFollowCount} />
            )}
            {me === UUID ? (
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
            UUID={UUID}
            updataCount={getFollowCount}
            modalType="follower"
            setModal={setModal}
          />
        )}
        {modal.following && (
          <FollowListModal
            UUID={UUID}
            updataCount={getFollowCount}
            modalType="following"
            setModal={setModal}
          />
        )}
      </div>
      <div className={styles.profile_postList}>
        <PostList
          data={data}
          fetchNextPage={fetchNextPage}
          hasNextPage={hasNextPage}
          navigate={navigate}
        />
      </div>
    </div>
  );
};

export default ProfilePage;
