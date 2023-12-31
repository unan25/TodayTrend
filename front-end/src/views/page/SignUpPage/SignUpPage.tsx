// react
import React, { useEffect, useState } from "react";

// react-router-dom
import { useNavigate } from "react-router-dom";

// redux
import { useDispatch, useSelector } from "react-redux";
import {
  signInSocialUser,
  updateUserInfo,
} from "../../../redux/_actions/user_action";
import { createAccount } from "../../../redux/_actions/user_action";

// custom hook
import { useUserInfoValidation } from "../../../hook/useUserInfoValidation";
import { useAccountValidation } from "../../../hook/useAccountValidation";

// style

import buttonStyle from "../../../module/styles/button.module.css";
import formStyle from "../../../module/styles/form.module.css";

// State
import { RootState } from "redux/store";
import AccountForm from "../../components/user/AccountForm/AccountForm";
import UserInfoForm from "../../components/user/UserInfoForm/UserInfoForm";
import { UserInfo, SocialUser } from "interface/UserInterface";
import axios from "axios";

function SignUpPage() {
  const userType = useSelector((state: RootState) => state.user.userType);
  const UUID = useSelector((state: RootState) => state.user.UUID);
  const email = useSelector((state: RootState) => state.user.email);
  // dispatch & state
  const dispatch = useDispatch<any>();

  // navigate
  const navigate = useNavigate();

  //------------------------------------------------------------------------------

  const [signInStep, setSignInStep] = useState(true);
  const [image, setImage] = useState<File[]>([]);

  const onClickHandler = (e: React.MouseEvent<HTMLButtonElement>) => {
    setSignInStep((prev) => !prev);
  };

  //------------------------------------------------------------------------------

  const {
    Fields: userInfoFields,
    handleChange: userInfoHandleChange,
    Message: userInfoMessage,
    IsValidated: userInfoIsValidated,
  } = useUserInfoValidation();

  const {
    Fields: accountFields,
    handleChange: accountHandleChange,
    Message: accountMessage,
    IsValidated: accountIsValidated,
  } = useAccountValidation();

  const getImageUrl = async (image: File[]) => {
    const formData = new FormData();

    formData.append("image", image[0]);

    const response = await axios.post("/api/images/profile", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return response.data.profileImage;
  };

  // sign-up
  const submitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // 로컬 회원가입

    if (userType !== "SOCIAL") {
      try {
        let account = {
          email: accountFields.email,
          password: accountFields.password,
        };

        const response = await dispatch(createAccount(account));

        let imageURL: string =
          "https://todaytrend.s3.ap-northeast-2.amazonaws.com/profile/04dbd59a-c0e5-459c-bb2a-3b672e28c373TT_Default_Profile.jpg";

        if (image.length) {
          imageURL = await getImageUrl(image);
        }

        let userInfo: UserInfo = {
          uuid: response.payload.UUID_temp,
          profileImage: imageURL,
          ...userInfoFields,
        };

        await dispatch(updateUserInfo(userInfo));

        navigate("/");
      } catch (err: any) {
        console.error(err);
      }
    }

    // 소셜 회원가입

    if (userType === "SOCIAL") {
      let account: SocialUser = {
        uuid: UUID,
        email: email,
      };

      dispatch(signInSocialUser(account));

      let imageURL: string =
        "https://todaytrend.s3.ap-northeast-2.amazonaws.com/profile/04dbd59a-c0e5-459c-bb2a-3b672e28c373TT_Default_Profile.jpg";

      if (image.length) {
        console.log(image);
        imageURL = await getImageUrl(image);
      }

      let userInfo: UserInfo = {
        uuid: UUID,
        profileImage: imageURL,
        ...userInfoFields,
      };

      try {
        const response = await dispatch(updateUserInfo(userInfo));
        if (response.meta.requestStatus === "fulfilled") navigate("/");
      } catch (err: any) {
        console.error(err);
      }
    }
  };

  // useEffect
  useEffect(() => {
    if (userType === "SOCIAL") {
      setSignInStep(false);
    }
  }, []);

  //------------------------------------------------------------------------------

  return (
    <div className="page-body">
      <form onSubmit={submitHandler} className={formStyle.mainForm}>
        {signInStep && userType !== "SOCIAL" ? (
          <AccountForm
            fields={accountFields}
            message={accountMessage}
            handleChange={accountHandleChange}
          />
        ) : (
          <UserInfoForm
            fields={userInfoFields}
            message={userInfoMessage}
            handleChange={userInfoHandleChange}
            image={image}
            setFunction={setImage}
          />
        )}
        {userType !== "SOCIAL" && (
          <button
            type="button"
            className={buttonStyle.submitButton}
            onClick={onClickHandler}
            disabled={!accountIsValidated}
          >
            {signInStep ? "다음 단계" : "이전 단계"}
          </button>
        )}
        {!signInStep && (
          <button
            type="submit"
            className={buttonStyle.submitButton}
            disabled={!userInfoIsValidated}
          >
            회원가입
          </button>
        )}
      </form>
    </div>
  );
}

export default SignUpPage;
