import React, { useEffect, useState } from "react";

export function useAccountValidation() {
  const [Fields, setFields] = useState({
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [validation, setValidation] = useState([false, false]);
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
      setValidation((prev) => [
        passwordRegex.test(value),
        ...validation.slice(1),
      ]);
    }

    if (field === "confirmPassword") {
      setMessage(
        value === Fields.password ? "" : "비밀번호가 일치하지 않습니다."
      );
      setValidation((prev) => [validation[0], value === Fields.password]);
    }
  };

  useEffect(() => {
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
