import React, { useEffect, useState } from "react";

// router-dom
import { useParams } from "react-router-dom";

// axios
import axios from "axios";

// redux
import { useSelector } from "react-redux";
import { RootState } from "redux/store";

// component
import OnChangeInput from "../../../views/components/common/OnChangeInput/OnChangeInput";

// hook
import { useUserInfoValidation } from "../../../hook/useUserInfoValidation";

// styles
import styles from "./UserInfoUpdatePage.module.css";
import buttonStyle from "../../../module/styles/button.module.css";

function UserInfoUpdatePage() {
  // parma
  const { nickname } = useParams();

  // state
  const UUID: string = useSelector((state: RootState) => state.user.UUID);

  const { Fields: fields, handleChange: setField } = useUserInfoValidation();
  const [profileImage, setProfileImage] = useState<string>("");

  // axios

  const getUserInfo = async (nickname: string) => {
    try {
      const response = (await axios.get(`/api/users/profile/${nickname}`)).data;

      setField("name")(response.name);
      setField("nickname")(response.nickname);
      setField("website")(response.website);
      setField("introduction")(response.introduction);
      setProfileImage(response.profileImage);
    } catch (err) {
      console.error();
    }
  };

  const updateUserInfo = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const data = {
        uuid: UUID,
        name: fields.name,
        nickname: fields.nickname,
        website: fields.website,
        introduction: fields.introduction,
        profileImage: profileImage,
      };

      const response = await axios.put("/api/users/updateProfile", data);

      alert(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (nickname) getUserInfo(nickname);
  }, []);

  useEffect(() => {
    console.log(fields);
  }, [fields]);

  return (
    <div className="page-body">
      <form onSubmit={updateUserInfo}>
        <OnChangeInput
          value={fields.name}
          placeholder="이름"
          onChange={setField("name")}
        />
        <OnChangeInput
          value={fields.nickname}
          placeholder="사용자 이름"
          onChange={setField("nickname")}
        />
        <OnChangeInput
          value={fields.website}
          placeholder="공유 링크"
          onChange={setField("website")}
        />
        <OnChangeInput
          value={fields.introduction}
          placeholder="소개글"
          onChange={setField("introduction")}
        />
        <button className={buttonStyle.submitButton}>프로필 수정</button>
      </form>
    </div>
  );
}

export default UserInfoUpdatePage;
