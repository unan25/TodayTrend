// react
import React, { useEffect, useState } from "react";

// components
import OnChangeInput from "../../../components/common/OnChangeInput/OnChangeInput";
import DropZone from "../../../components/common/DropZone/ProfileDropZone/ProfileDropZone";
import SelectBox from "../../../components/common/SelectBox/SelectBox";
import AlertBox from "../../../components/common/AlertBox/AlertBox";

// type
import { UserInfo } from "interface/UserInterface";

// styles
import formStyle from "../../../../module/styles/form.module.css";
import axios from "axios";
import { debounce } from "../../../../module/functions/debounce";

type Props = {
  fields: UserInfo;
  message: string;
  handleChange: (field: string) => void;
  image: File[];
  setFunction: (file: File[]) => void;
};

const UserInfoForm: React.FC<Props> = ({
  fields,
  message,
  handleChange,
  image,
  setFunction,
}) => {
  // State
  const genders = ["", "MALE", "FEMALE"];
  const [duplicationMessage, setDuplicationMessage] = useState<
    string | undefined
  >();
  const [isDuplicated, setIsDuplicated] = useState<boolean | undefined>();

  const checkDuplication = async () => {
    try {
      const response = await axios.get(
        `/api/users/checkNickname?nickname=${fields.nickname}`
      );

      setDuplicationMessage(response.data);
      setIsDuplicated(false);
    } catch (err: any) {
      console.error(err);
      if (err.response.status === 409) {
        setDuplicationMessage(err.response.data);
        setIsDuplicated(true);
      }
    }
  };

  useEffect(() => {
    if (fields.nickname !== "") {
      debounce(checkDuplication, 0)(fields.nickname);
      console.log(fields.nickname);
    }
  }, [fields.nickname]);

  //------------------------------------------------------------------------------

  return (
    <div className={formStyle.signup_form_userInfo}>
      <DropZone setFunction={setFunction} image={image} />
      <OnChangeInput
        type="text"
        placeholder="이름"
        value={fields.name}
        onChange={handleChange("name")!}
      />
      <div className={formStyle.signup_nickname}>
        {duplicationMessage && (
          <AlertBox isError={isDuplicated} message={duplicationMessage} />
        )}
        <OnChangeInput
          type="text"
          placeholder="사용자 이름"
          value={fields.nickname}
          onChange={handleChange("nickname")!}
          required={true}
        />
      </div>
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
