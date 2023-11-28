import React, { useEffect } from "react";

// axios
import axios from "axios";

// redux
import { signInSocialUser } from "../../../../redux/_actions/user_action";

// service
import GoogleLoginService from "./GoogleLoginService";

// env
const GOOGLE_ID = process.env.GOOGLE_ID;

// URL
const GoogleAccessTokenUrl: string = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_ID}&response_type=token&redirect_uri=http://localhost:3000/signin&scope=https://www.googleapis.com/auth/userinfo.email`;

// images
import googleLogin from "../../../../images/google.png";
import { useDispatch } from "react-redux";

function GoogleLogin() {
  const dispatch = useDispatch<any>();

  const onClickHandler = async () => {
    window.location.assign(GoogleAccessTokenUrl);
  };

  const fetchUser = async (accessToken: string) => {
    const response = await GoogleLoginService.getUserInfo(accessToken);
    dispatch(signInSocialUser(response!))

    return response;
  } 

  // signIn Success
  useEffect(() => {
    const hash = window.location.hash.substring(1);
    const urlParams = new URLSearchParams(hash);
    const accessToken = urlParams.get("access_token");

    if (accessToken) {
      const response = fetchUser(accessToken)
      
      // GoogleLoginService.logOut(accessToken);
    }
  }, []);

  return (
    <a>
      <img src={googleLogin} onClick={onClickHandler} />
    </a>
  );
}

export default GoogleLogin;
