import React, { useEffect } from "react";

// redux
import {
  logOut,
  signInSocialUser,
} from "../../../../redux/_actions/user_action";

// client_id
const Kakao_ID = process.env.KAKAO_ID!;

// env
const redirect_uri = process.env.REDIRECT_URL!;

// URL
const KakaoAuthorizedCodeUrl: string = `https://kauth.kakao.com/oauth/authorize?client_id=${Kakao_ID}&redirect_uri=${redirect_uri}&response_type=code`;

// service
import KakaoLoginService from "./KakaoLoginService";

// images
import kakaoLogin from "../../../../images/kakao.png";
import { useDispatch } from "react-redux";

function KakaoLogin() {
  const dispatch = useDispatch<any>();

  const onClickHandler = async () => {
    window.location.assign(KakaoAuthorizedCodeUrl);
  };

  // dispatch(signInSocialUser(userInfo));

  const kakaoSocialLogin = async (code: string) => {
    const accessToken = await KakaoLoginService.getAccessToken(code);
    if (accessToken) {
      const userInfo = await KakaoLoginService.getUserInfo(accessToken);
      console.log(userInfo);
      // KakaoLoginService.logOut(accessToken);
    }
  };

  // signIn Success
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get("code");

    if (code) {
      kakaoSocialLogin(code);
      // window.location.assign("http://localhost:3000/");
    }
  }, []);

  return (
    <a>
      <img src={kakaoLogin} onClick={onClickHandler} />
    </a>
  );
}

export default KakaoLogin;
