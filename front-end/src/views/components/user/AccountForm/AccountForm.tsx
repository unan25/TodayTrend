// react
import React, { useEffect, useState } from "react";

// component
import OnChangeInput from "../../common/OnChangeInput/OnChangeInput";
import AlertBox from "../../common/AlertBox/AlertBox";

// style
import formStyle from "../../../../module/styles/form.module.css";
import styles from "./AccountForm.module.css";

// type
import { Account } from "interface/UserInterface";

// img

import { debounce } from "../../../../module/functions/debounce";
import axios from "axios";

type Props = {
  fields: Account;
  message: string | undefined;
  handleChange: (field: string) => void;
};

const AccountForm: React.FC<Props> = ({ fields, message, handleChange }) => {
  const [duplicationMessage, setDuplicationMessage] = useState<
    string | undefined
  >();
  const [isDuplicated, setIsDuplicated] = useState<boolean | undefined>();

  const checkDuplication = async () => {
    try {
      const response = await axios.get(
        `/api/auth/checkEmail?email=${fields.email}`
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
    if (fields.email !== "") {
      debounce(checkDuplication, 0)(fields.email);
      console.log(fields.email);
    }
  }, [fields.email]);

  return (
    <div className={formStyle.signup_form_account}>
      <div className={formStyle.signup_email}>
        {duplicationMessage && (
          <AlertBox isError={isDuplicated} message={duplicationMessage} />
        )}
        <OnChangeInput
          type="email"
          placeholder="이메일"
          value={fields.email}
          required={true}
          onChange={handleChange("email")!}
        />
      </div>
      <OnChangeInput
        type="password"
        placeholder="비밀번호"
        value={fields.password}
        required={true}
        onChange={handleChange("password")!}
      />
      <OnChangeInput
        type="password"
        placeholder="비밀번호 확인"
        value={fields.confirmPassword}
        required={true}
        onChange={handleChange("confirmPassword")!}
      />
      <AlertBox isError={true} message={message} />
    </div>
  );
};

export default AccountForm;
