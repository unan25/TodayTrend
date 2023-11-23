// react
import React, { useEffect, useState } from "react";

// react-router-dom
import { useNavigate } from "react-router-dom";

// redux
import { useDispatch, useSelector } from "react-redux";
import { updateUserInfo } from "../../../redux/_actions/user_action";

// custom hook
import { useUserInfoValidation } from "../../../hook/useUserInfoValidation";

// style
import styles from "./CreateUserInfoPage.module.css";
import buttonStyle from "../../../module/styles/button.module.css";
import formStyle from "../../../module/styles/form.module.css";

// components
import OnChangeInput from "../../components/OnChangeInput/OnChangeInput";
import DropZone from "../../components/DropZone/ProfileDropZone/ProfileDropZone";
import SelectBox from "../../components/SelectBox/SelectBox";
import AlertBox from "../../components/AlertBox/AlertBox";

// State
import { RootState } from "redux/store";

function AccountPage() {
  // state
  const UUID = useSelector((state: RootState) => state.user.UUID);
  // dispatch & state
  const dispatch = useDispatch<any>();

  const signUpSuccess = useSelector((state: any) => state.user.signUpSuccess);

  // navigate
  const navigate = useNavigate();

  // State
  const [UploadedFile, SetUploadedFile] = useState("");
  const genders = ["", "MALE", "FEMALE"];

  //------------------------------------------------------------------------------

  const { Fields, handleChange, Message, IsValidated } =
    useUserInfoValidation();

  // sign-up
  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let userInfo = {
      uuid: UUID,
      name: Fields.name,
      nickname: Fields.nickname,
      phone: Fields.phone,
      gender: Fields.gender,
      birth: Fields.birth,
      website: Fields.website,
      introduction: Fields.introduction,
    };

    try {
      dispatch(updateUserInfo(userInfo));
      navigate("/");
    } catch (err: any) {
      console.error(err);
    }
  };

  //------------------------------------------------------------------------------

  return (
    <div className="page-body">
      <form onSubmit={submitHandler} className={formStyle.mainForm}>
        <DropZone setFunction={SetUploadedFile} />
        <OnChangeInput
          type="text"
          placeholder="이름"
          onChange={handleChange("name")}
        />
        <OnChangeInput
          type="text"
          placeholder="사용자 이름"
          onChange={handleChange("nickname")}
          required={true}
        />
        <OnChangeInput
          type="date"
          placeholder="생년월일"
          onChange={handleChange("birth")}
        />
        <OnChangeInput
          type="text"
          placeholder="전화번호"
          onChange={handleChange("phone")}
        />
        <SelectBox
          options={genders}
          onChange={handleChange("gender")}
          required={true}
        />
        <OnChangeInput
          type="url"
          placeholder="링크"
          onChange={handleChange("website")}
        />
        <OnChangeInput
          type="text"
          placeholder="소개"
          onChange={handleChange("introduction")}
        />
        <AlertBox isError={true} message={Message} />
        <button
          type="submit"
          className={buttonStyle.submitButton}
          disabled={!IsValidated}
        >
          등록
        </button>
      </form>
    </div>
  );
}

export default AccountPage;
