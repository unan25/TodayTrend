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

  const [IsValidated, setIsValidated] = useState(false);

  const [Message, setMessage] = useState("사용자 정보를 입력해 주세요");

  // curring - ex) handleChange("email")(e.target.value)
  const handleChange = (field: string) => (value: string) => {
    // [field] : value - Computed Property Names
    setFields((prevFields) => ({ ...prevFields, [field]: value }));

    if (field === "nickname") {
      setMessage(value === "" ? "사용자 이름은 필수 사항입니다." : "");
    }

    if (field === "gender") {
      setMessage(value === "" ? "성별은 필수 사항입니다." : "");
    }
  };

  useEffect(() => {
    if (Fields.gender !== "" && Fields.nickname !== "") {
      setIsValidated(true);
    } else {
      setIsValidated(false);
    }
  }, [Fields.gender, Fields.nickname]);

  return { Fields, handleChange, Message, IsValidated };
}
