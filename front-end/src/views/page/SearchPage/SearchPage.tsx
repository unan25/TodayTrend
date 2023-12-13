import axios from 'axios';
import React, { useEffect, useState } from 'react';
import styles from './SearchPage.module.css';
import { useNavigate, useParams } from 'react-router-dom';

export interface userTagType {
  uuid: string;
  nickname: string;
  profileImage: string;
}

export interface hashTagType {
  hashTag: string;
}

const SearchPage: React.FC = () => {
  const tag = useParams();
  const navigate = useNavigate();
  const [userTag, setUserTag] = useState<string>('');
  const [hashTag, setHashTag] = useState<string>('');
  const [userData, setUserData] = useState<userTagType[]>([]); //드롭다운리스트
  const [hashData, setHashData] = useState<string[]>([]);
  const [isInput, setIsInput] = useState<boolean>(false); //인풋이 있는지

  const onChangeHandler = (e: any) => {
    const newContent = e.target.value;
    if (newContent.charAt(0) === '#') {
      setHashTag(newContent.substring(1));
    } else {
      setUserTag(newContent);
    }
    setIsInput(newContent.trim() !== '');
  };

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get(`api/users/nickname/${userTag}`);
        setUserData(response.data);
      } catch (error) {
        console.log('유저검색 리스트 못 받는 중', error);
        setUserData([]);
      }
    };
    fetchUserData();
  }, [userTag]);

  useEffect(() => {
    const fetchPostData = async () => {
      try {
        const response = await axios.get(`api/post/hashtag?hashtag=${hashTag}`);
        setHashData(response.data);
        console.log(hashData);
      } catch (error) {
        console.log('포스트검색 리스트 못 받는 중', error);
        setHashData([]);
      }
    };
    fetchPostData();
  }, [hashTag]);

  useEffect(() => {
    console.log('usertag:' + userTag);
  }, [userTag]);
  useEffect(() => {
    console.log('hashtag:' + hashTag);
  }, [hashTag]);

  return (
    <div className="page-body">
      <input
        className={styles.input}
        type="search"
        id="searchInput"
        name="query"
        placeholder="검색어를 입력하세요. #으로 시작하면 해시태그 검색"
        onInput={onChangeHandler}
      />
      {isInput && (
        <div className={styles.dropDownBox}>
          {userData && userTag && (
            <>
              {userData.map((user) => (
                <div
                  key={user.uuid}
                  className={styles.dropDownItem}
                  onClick={() => navigate(`/profile/${user.nickname}`)}
                >
                  <img
                    className={styles.profileimage}
                    src={user.profileImage}
                  />
                  <span className={styles.nickname}>{user.nickname}</span>
                </div>
              ))}
            </>
          )}
          {hashData && hashTag&&(
            <>
              {hashData.map((tag, i) => (
                <div
                  key={i}
                  className={styles.dropDownItem}
                  onClick={() => navigate(`/search/${tag}`)}
                >
                  <span className={styles.nickname}>{tag}</span>
                </div>
              ))}
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default SearchPage;

<div>
  <img className={styles.profileimage} alt="유저사진"></img>
  <span>유저닉네임</span>
</div>;

<div>
  <span>해시태그이름</span>
</div>;
