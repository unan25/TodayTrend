// react
import React, { useState } from "react";

// components
import OnChangeInput from "../../components/OnChangeInput/OnChangeInput";
import DropZone from "../../components/DropZone/ProfileDropZone/ProfileDropZone";
import SelectBox from "../../components/SelectBox/SelectBox";
import AlertBox from "../../components/AlertBox/AlertBox";

// type
import { UserInfo } from "interface/UserInterface";

// styles
import formStyle from "../../../module/styles/form.module.css";

type Props = {
  fields: UserInfo;
  message: string;
  handleChange: (field: string) => void;
};

const UserInfoForm: React.FC<Props> = ({ fields, message, handleChange }) => {
  // State
  const [UploadedFile, SetUploadedFile] = useState("");
  const genders = ["", "MALE", "FEMALE"];

  //------------------------------------------------------------------------------

  return (
    <div className={formStyle.signup_form}>
      <DropZone setFunction={SetUploadedFile} />
      <OnChangeInput
        type="text"
        placeholder="이름"
        value={fields.name}
        onChange={handleChange("name")!}
      />
      <OnChangeInput
        type="text"
        placeholder="사용자 이름"
        value={fields.nickname}
        onChange={handleChange("nickname")!}
        required={true}
      />
      <OnChangeInput
        type="date"
        placeholder="생년월일"
        value={fields.birth}
        onChange={handleChange("birth")!}
      />
      <OnChangeInput
        type="text"
        placeholder="전화번호"
        value={fields.phone}
        onChange={handleChange("phone")!}
      />
      <SelectBox
        options={genders}
        onChange={handleChange("gender")!}
        value={fields.gender}
        required={true}
      />
      <OnChangeInput
        type="url"
        placeholder="링크"
        value={fields.website}
        onChange={handleChange("website")!}
      />
      <OnChangeInput
        type="text"
        placeholder="소개"
        value={fields.introduction}
        onChange={handleChange("introduction")!}
      />
      <AlertBox isError={true} message={message} />
    </div>
  );
};

export default UserInfoForm;
