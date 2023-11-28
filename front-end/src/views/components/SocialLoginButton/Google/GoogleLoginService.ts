// axios
import axios from "axios";

// env
const GOOGLE_ID = process.env.GOOGLE_ID;
const GoogleUserInfoUrl: string = process.env.GOOGLE_USERINFO_URL!;
const GoogleLogOutUrl: string = process.env.GOOGLE_LOGOUT_URL!;
const app_host: string = process.env.APP_HOST!;

const GoogleLoginService = {
  getUserInfo: async (accessToken: string) => {
    try {
      const response = await axios.get(GoogleUserInfoUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      const userInfo = {
        email: response.data.email,
      };

      return userInfo;
    } catch (err) {
      alert("토큰을 가져오지 못했습니다.");
      window.location.assign(app_host);
    }
  },

  logOut: async (accessToken: string) => {
    await axios
      .post(GoogleLogOutUrl + accessToken)
      .then(() => {
        // window.location.assign("http://localhost:3000/");
      })
      .catch(() => {
        alert("로그아웃에 실패했습니다.");
      });
  },
};

export default GoogleLoginService;
