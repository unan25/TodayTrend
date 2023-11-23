import React, { useEffect } from "react";

// axios
import axios from "axios";

// images
import googleLogin from "../../../images/google.png";

const GOOGLE_ID =
  "191179853439-7ohg5gg524p7l601bue6680a0gs3234j.apps.googleusercontent.com";

// URL
const GoogleAccessTokenUrl: string = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_ID}&response_type=token&redirect_uri=http://localhost:3000/signin&scope=https://www.googleapis.com/auth/userinfo.email`;
const GoogleUserInfoUrl: string =
  "https://www.googleapis.com/oauth2/v2/userinfo";
const GoogleLogOutUrl: string = "https://oauth2.googleapis.com/revoke?token=";

function GoogleLogin() {
  const onClickHandler = async () => {
    window.location.assign(GoogleAccessTokenUrl);
  };

  const getUserInfo = async (accessToken: string) => {
    await axios
      .get(GoogleUserInfoUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        alert("oAuth token expired");
        window.location.assign("http://localhost:3000");
      });
  };

  const logOut = async (accessToken: string) => {
    await axios
      .post(GoogleLogOutUrl + accessToken)
      .then(() => {
        // window.location.assign("http://localhost:3000/");
      })
      .catch(() => {
        alert("로그아웃에 실패했습니다.");
      });
  };
  // signIn Success
  useEffect(() => {
    const hash = window.location.hash.substring(1);
    const urlParams = new URLSearchParams(hash);
    const accessToken = urlParams.get("access_token");

    if (accessToken) {
      console.log(accessToken);
      getUserInfo(accessToken);
      // logOut(accessToken);
    }
  }, []);

  return (
    <a>
      <img src={googleLogin} onClick={onClickHandler} />
    </a>
  );
}

export default GoogleLogin;
