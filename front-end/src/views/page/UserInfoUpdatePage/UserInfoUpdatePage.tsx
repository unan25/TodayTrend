import React, { useEffect, useState } from "react";

// router-dom
import { useParams , useNavigate} from "react-router-dom";

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
import userInfoButtonStyle from "./UserInfoUpdatePage.module.css";

function UserInfoUpdatePage() {
  // parma
  const { nickname } = useParams();
  const navigate = useNavigate();
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

  const passwordChange = () =>{
    navigate('/change-password');
  };

  return (
    <div className="page-body">
      <div className={userInfoButtonStyle.hiddendiv}></div>
      <form className= {userInfoButtonStyle.userinfo_from} onSubmit={updateUserInfo}>
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
          <div className={userInfoButtonStyle.userinfo_page_body_form}>
            <button type="button" className={userInfoButtonStyle.userinfo_button} onClick={passwordChange}>패스워드 변경</button>
            <button type="submit" className={userInfoButtonStyle.userinfo_button}>프로필 수정</button>
          </div>
        </form>
    </div>
  );
}

export default UserInfoUpdatePage;
