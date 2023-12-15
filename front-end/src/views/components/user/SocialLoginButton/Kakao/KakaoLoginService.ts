// axios
import axios from "axios";

// module
import { makeFormData } from "../../../../../module/functions/makeformData";

// Client_Id
const Kakao_ID = process.env.KAKAO_ID!;

// env
const redirect_uri = process.env.REDIRECT_URL!;
const KakaoAccessTokenUrl = process.env.KAKAO_ACCESSTOKEN_URL!;
const KakaoUserInfoUrl = process.env.KAKAO_USERINFO_URL!;
const KakaoLogOutUrl: string = process.env.KAKAO_LOGOUT_URL!;
const KakaoSecretKey: string = process.env.KAKAO_SECRET_KEY!;

// url
const KakaoSessionUrl: string = `https://kauth.kakao.com/oauth/logout?client_id=${Kakao_ID}&logout_redirect_uri=${redirect_uri}`;

const KakaoLoginService = {
  getAccessToken: async (code: string) => {
    try {
      const data = {
        grant_type: "authorization_code",
        client_id: Kakao_ID,
        redirect_uri: redirect_uri,
        client_secret: KakaoSecretKey,
        code: code,
      };

      const accessTokenData = await axios.post(
        KakaoAccessTokenUrl,
        makeFormData(data),
        {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        }
      );

      const accessToken: string = accessTokenData.data.access_token;

      return accessToken;
    } catch (err) {
      console.log("토큰을 가져오지 못했습니다.");
    }
  },

  getUserInfo: async (accessToken: string) => {
    try {
      const userData = await axios.get(KakaoUserInfoUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      const userInfo = {
        email: userData.data.kakao_account.email,
      };

      return userInfo;
    } catch (err) {
      console.log("유저 정보를 불러오지 못했습니다.");
    }
  },

  // access token 만을 만료시킨다. 브라우저와 연결된 로그인은 쿠키를 삭제해야 한다.
  logOut: async (accessToken: string) => {
    try {
      const response = await axios.post(KakaoLogOutUrl, null, {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Authorization: `Bearer ${accessToken}`,
        },
      });

      window.location.assign(KakaoSessionUrl);

      return response;
    } catch (error) {
      console.error("로그아웃에 실패했습니다.");
    }
  },
};

{
  uuid: "uuid";
  userType: "local";
}

export default KakaoLoginService;
