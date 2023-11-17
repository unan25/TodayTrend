import React, { useEffect, useState } from "react";

export function useAccountValidation() {
  const [Fields, setFields] = useState({
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [IsValidated, setIsValidated] = useState(false);
  const [Message, setMessage] = useState("계정 정보를 입력해 주세요");

  // curring - ex) handleChange("email")(e.target.value)
  const handleChange = (field: string) => (value: string) => {
    // [field] : value - Computed Property Names
    setFields((prevFields) => ({ ...prevFields, [field]: value }));

    if (field === "password") {
      const passwordRegex =
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

      setMessage(
        passwordRegex.test(value)
          ? ""
          : "비밀번호는 최소 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다."
      );
    }

    if (field === "confirmPassword") {
      setMessage(
        value === Fields.password ? "" : "비밀번호가 일치하지 않습니다."
      );
    }
  };

  useEffect(() => {
    if (
      Fields.email.trim() !== "" &&
      Fields.password.trim() !== "" &&
      Fields.confirmPassword.trim() !== ""
    ) {
      setIsValidated(true);
    } else {
      setIsValidated(false);
    }
  }, [Fields.email, Fields.password, Fields.confirmPassword]);

  return { Fields, handleChange, Message, IsValidated };
}
