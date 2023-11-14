// react
import React, { useEffect, useState } from "react";

// react-router-dom
import { useNavigate } from "react-router-dom";

// redux & action
import { useDispatch, useSelector } from "react-redux";
import { updateUserInfo } from "../../../state/_actions/user_action";

// style
import styles from "./CreateUserInfoPage.module.css";

import OnChangeInput from "../../components/OnChangeInput/OnChangeInput";

import DropZone from "../../components/DropZone/DropZone";
import SelectBox from "../../components/SelectBox/SelectBox";

function AccountPage() {
  // dispatch & state
  const dispatch = useDispatch<any>();

  const signUpSuccess = useSelector((state: any) => state.user.signUpSuccess);

  // navigate
  const navigate = useNavigate();

  // State
  const [UploadedFile, SetUploadedFile] = useState("");
  const [Name, SetName] = useState("");
  const [Nickname, SetNickname] = useState("");
  const [PhoneNumber, SetPhoneNumber] = useState("");
  const [Birth, SetBirth] = useState("");
  const [Website, SetWebsite] = useState("");
  const [Gender, SetGender] = useState("MALE");
  const genders = ["MALE", "FEMALE"];
  const [Introduction, SetIntroduction] = useState("");

  //------------------------------------------------------------------------------

  const nameHandler = (name: string) => {
    SetName(name);
  };

  const nicknameHandler = (nickname: string) => {
    SetNickname(nickname);
  };

  const phoneNumberHandler = (phone: string) => {
    SetPhoneNumber(phone);
  };

  const birthHandler = (birth: string) => {
    SetBirth(birth);
  };

  const websiteHandler = (website: string) => {
    SetWebsite(website);
  };

  const genderHandler = (gender: string) => {
    SetGender(gender);
  };

  const introductionHandler = (introdcution: string) => {
    SetIntroduction(introdcution);
  };

  // sign-up
  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let userInfo = {
      name: Name,
      nickname: Nickname,
      phone: PhoneNumber,
      gender: Gender,
      birth: Birth,
      website: Website,
      introduction: Introduction,
    };

    dispatch(updateUserInfo(userInfo));
  };

  //------------------------------------------------------------------------------\
  // state control
  useEffect(() => {
    if (signUpSuccess) navigate("/");
  }, [signUpSuccess]);

  //------------------------------------------------------------------------------

  return (
    <div className="page-body">
      <form onSubmit={submitHandler} className={styles.mainForm}>
        <DropZone setFunction={SetUploadedFile} />
        <OnChangeInput type="text" placeholder="이름" onChange={nameHandler} />
        <OnChangeInput
          type="text"
          placeholder="사용자 이름"
          onChange={nicknameHandler}
        />
        <OnChangeInput
          type="date"
          placeholder="생년월일"
          onChange={birthHandler}
        />
        <OnChangeInput
          type="text"
          placeholder="전화번호"
          onChange={phoneNumberHandler}
        />
        <SelectBox options={genders} onChange={genderHandler} />
        <OnChangeInput
          type="url"
          placeholder="링크"
          onChange={websiteHandler}
        />
        <OnChangeInput
          type="text"
          placeholder="소개"
          onChange={introductionHandler}
        />
        <button type="submit">등록</button>
      </form>
    </div>
  );
}

export default AccountPage;
