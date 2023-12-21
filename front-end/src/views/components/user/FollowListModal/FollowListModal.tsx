import React, { useEffect, useState } from "react";

//
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

//
import axios from "axios";

//
import { useParams } from "react-router-dom";

//
import styles from "./FollowListModal.module.css";
import FollowButton from "../FollowButton/FollowButton";

type Props = {
  UUID: string;
  setModal: React.Dispatch<React.SetStateAction<ListModal>>;
  modalType: string;
  updataCount: () => void;
};

type ListModal = {
  follower: boolean;
  following: boolean;
};

type Users = {
  nickName: string;
  profileImage: string;
  uuid: string;
};

const FollowListModal: React.FC<Props> = ({
  UUID,
  setModal,
  modalType,
  updataCount,
}) => {
  // state
  const me = useSelector((state: RootState) => state.user.UUID);

  const [list, setList] = useState<Users[]>();

  const [filteredList, setFilteredList] = useState<Users[]>();

  const modalHandler = () => {
    setModal({ follower: false, following: false });
  };

  const searchUsers = (e: React.ChangeEvent<HTMLInputElement>) => {
    const keyword = e.currentTarget.value.toLowerCase();

    const filteredUsers = list?.filter((user) =>
      user.nickName.toLowerCase().includes(keyword)
    );

    setFilteredList(filteredUsers);
  };

  const renderUsers = () => {
    return filteredList?.map((e, i) => {
      return (
        <div className={styles.modal_body__list_user} key={i}>
          <div className={styles.modal_body__list_section1}>
            <img
              className={styles.modal_body__list_section1__profileImage}
              src={e.profileImage}
              alt={e.nickName}
            />
            <div className={styles.modal_body__list_section1__nickName}>
              {e.nickName}
            </div>
          </div>
          <FollowButton from={me} to={e.uuid} updataCount={updataCount} />
        </div>
      );
    });
  };

  const getFollwerList = async () => {
    try {
      const response = await axios.get(`/api/users/follower-list/${UUID}`);
      setList(response.data);
      setFilteredList(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const getFollwingList = async () => {
    try {
      const response = await axios.get(`/api/users/following-list/${UUID}`);
      setList(response.data);
      setFilteredList(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (modalType === "follower") getFollwerList();
    if (modalType === "following") getFollwingList();
  }, []);

  return (
    <div className={styles.modal}>
      <div className={styles.modal_header}>
        <button
          className={styles.modal_btn_close}
          type="button"
          onClick={modalHandler}
        >
          X
        </button>
      </div>
      <div className={styles.modal_body}>
        <input
          className={styles.modal_body__search}
          onChange={searchUsers}
          placeholder="검색"
        />
        <div className={styles.modal_body__list}>{renderUsers()}</div>
      </div>
    </div>
  );
};

export default FollowListModal;
