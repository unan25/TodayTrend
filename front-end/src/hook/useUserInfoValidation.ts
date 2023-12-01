import React, { useEffect, useState } from "react";

export function useUserInfoValidation() {
  const [Fields, setFields] = useState({
    name: "",
    nickname: "",
    phone: "",
    gender: "",
    birth: "",
    website: "",
    introduction: "",
  });

  const [validation, setValidation] = useState([false, false]);
  const [IsValidated, setIsValidated] = useState(false);
  const [Message, setMessage] = useState("사용자 정보를 입력해 주세요");

  // curring - ex) handleChange("email")(e.target.value)
  const handleChange = (field: string) => (value: string) => {
    // [field] : value - Computed Property Names
    setFields((prevFields) => ({ ...prevFields, [field]: value }));

    if (field === "nickname") {
      setMessage(value === "" ? "사용자 이름은 필수 사항입니다." : "");
      setValidation((prev) => [value !== "", ...validation.slice(1)]);
    }

    if (field === "gender") {
      setMessage(value === "" ? "성별은 필수 사항입니다." : "");
      setValidation((prev) => [validation[0], value !== ""]);
    }
  };

  useEffect(() => {
    console.log(validation);
    for (let i of validation) {
      if (i === false) {
        setIsValidated(false);
        break;
      }
      setIsValidated(true);
    }
  }, [validation]);

  return { Fields, handleChange, Message, IsValidated };
}
