import axios from "axios";

// 응답 인터셉터 등록
axios.interceptors.response.use(
  // 성공적인 응답의 경우 그대로 반환
  (response) => response,
  // 에러 응답의 경우
  async (error) => {
    const {
      config,
      response: { status },
    } = error;

    // 401 or 500 에러 체크
    if (error.response.status === 401) {
      try {
        // 토큰 재발급을 위한 컨트롤러 호출
        const tokenResponse = await axios.post("/api/auth/refresh");

        // 토큰 재발급 성공 시
        if (tokenResponse.status === 204) {
          const originalRequest = error.config;
          // 새로 발급받은 토큰으로 기존 요청을 재시도
          originalRequest.headers.Authorization = `Bearer ${tokenResponse.data.access_token}`;

          // 재시도한 요청 반환
          return axios(originalRequest);
        } else {
          // 토큰 재발급 실패 시 로그아웃 또는 다른 로직 수행
          console.error("Token refresh failed");
          // 예: 로그아웃 등의 로직 추가
        }
      } catch (refreshError) {
        console.error("Token refresh error:", refreshError);
        // 예: 로그아웃 등의 로직 추가
      }
    }

    // 401 에러가 아닌 다른 에러는 그대로 반환
    return Promise.reject(error);
  }
);

export default axios;
