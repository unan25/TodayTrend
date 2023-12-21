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

export interface searchUserType {
  uuid: string;
  nickname: string;
  profileImage: string;
}

const SearchPage: React.FC = () => {
  const navigate = useNavigate();
  const [userTag, setUserTag] = useState<string>('');
  const [hashTag, setHashTag] = useState<string>('');
  const [userData, setUserData] = useState<userTagType[]>([]); //드롭다운리스트
  const [hashData, setHashData] = useState<string[]>([]);
  const [isInput, setIsInput] = useState<boolean>(false); //인풋이 있는지
  const [searchUser, setSearchUser] = useState<searchUserType[]>([]);
  const [searchHash, setSearchHash] = useState<string[]>([]);

  // 최근 검색어
  useEffect(() => {
    setSearchUser(JSON.parse(sessionStorage.getItem('searchUser') || '[]'));
    setSearchHash(JSON.parse(sessionStorage.getItem('searchHash') || '[]'));
  }, []);

  const onClickUserHandler = (user: userTagType) => () => {
    sessionStorage.setItem(
      'searchUser',
      JSON.stringify(Array.from(new Set([user, ...searchUser])))
    );
    navigate(`/profile/${user.nickname}`);
  };
  const onClickHashHandler = (hashtag: string) => () => {
    sessionStorage.setItem(
      'searchHash',
      JSON.stringify(Array.from(new Set([hashtag, ...searchHash])))
    );
    navigate(`/search/${hashtag}`);
  };
  // 검색로직
  const onChangeHandler = (e: any) => {
    var newContent = e.target.value;
    const hasSpace = /\s/.test(newContent);
    console.log(hasSpace);
    //모든 공백 제거
    if (hasSpace) {
      newContent = newContent.replace(/\s/g, '');
      console.log(newContent);
    }
    if (newContent.charAt(0) === '#') {
      setHashTag(newContent.substring(1));
    } else if (newContent.charAt(0) === '@') {
      setUserTag(newContent.substring(1));
    }
    setIsInput(newContent.trim() !== '');
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      // 사용자가 Enter를 눌렀을 때 실행할 로직을 여기에 추가
    }
  };

  const removeUser = (user: userTagType) => (e: React.MouseEvent) => {
    e.stopPropagation();
    const updatedSearchUser = searchUser.filter(
      (data) => data.uuid !== user.uuid
    );
    setSearchUser(updatedSearchUser);
    sessionStorage.setItem('searchUser', JSON.stringify(updatedSearchUser));
  };

  const removeHash = (hashtag: string) => (e: React.MouseEvent) => {
    e.stopPropagation();
    const updatedSearchHash = searchHash.filter((data) => data !== hashtag);
    setSearchHash(updatedSearchHash);
    sessionStorage.setItem('searchHash', JSON.stringify(updatedSearchHash));
  };

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        if (userTag != '') {
          const response = await axios.get(`api/users/nickname/${userTag}`);
          setUserData(response.data);
        }
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
        if (hashTag != '') {
          const response = await axios.get(
            `api/post/hashtag?hashtag=${hashTag}`
          );
          setHashData(response.data);
        }
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
        placeholder=" @와 #으로 검색어를 입력하세요."
        onInput={onChangeHandler}
        onKeyDown={handleKeyDown}
      />
      {!isInput && (
        <div className={styles.recentSearch}>
          <div className={styles.recentSearchBox}>
            <div className={styles.dropDownBox}>
              {searchUser && (
                <>
                  <div className={styles.searchTitle}>최근 유저 검색 목록</div>
                  {searchUser.map((user, i) => (
                    <div
                      key={user.uuid}
                      className={styles.dropDownItem}
                      onClick={() => navigate(`/profile/${user.nickname}`)}
                    >
                      <img
                        className={styles.profileimage}
                        src={user.profileImage}
                      />
                      <span className={styles.nickname}>@{user.nickname}</span>
                      <button
                        className={styles.removeData}
                        onClick={removeUser(user)}
                      >
                        {' '}
                        X{' '}
                      </button>
                    </div>
                  ))}
                </>
              )}
            </div>
          </div>
          <div className={styles.recentSearchBox}>
            <div className={styles.dropDownBox}>
              {searchHash && (
                <>
                  <div className={styles.searchTitle}>
                    최근 해시태그 검색 목록
                  </div>
                  {searchHash.map((tag, i) => (
                    <div
                      key={i}
                      className={styles.dropDownItem}
                      onClick={() => navigate(`/search/${tag}`)}
                    >
                      <span className={styles.hashtag}>#{tag}</span>
                      <button
                        className={styles.removeData}
                        onClick={removeHash(tag)}
                      >
                        {' '}
                        X{' '}
                      </button>
                    </div>
                  ))}
                </>
              )}
            </div>
          </div>
        </div>
      )}{' '}
      {isInput && (
        <div className={styles.dropDownBox}>
          {userData && userTag && (
            <>
              {userData.map((user) => (
                <div
                  key={user.uuid}
                  className={styles.dropDownItem}
                  onClick={onClickUserHandler(user)}
                >
                  <img
                    className={styles.profileimage}
                    src={user.profileImage}
                  />
                  <span className={styles.nickname}>@{user.nickname}</span>
                </div>
              ))}
            </>
          )}
          {hashData && hashTag && (
            <>
              {hashData.map((tag, i) => (
                <div
                  key={i}
                  className={styles.dropDownItem}
                  onClick={onClickHashHandler(tag)}
                >
                  <span className={styles.hashtag}>#{tag}</span>
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
