import React from "react";
import { Route, Routes } from "react-router-dom";

// css
import styles from "./App.module.css";

// pages
import LandingPage from "./views/page/LandingPage/LandingPage";
import SignUpPage from "./views/page/SignUpPage/SignUpPage";
import SignInPage from "./views/page/SignInPage/SignInPage";
import UploadPostPage from "./views/page/UploadPostPage/UploadPostPage";
import SearchPage from "./views/page/SearchPage/SearchPage";
import SearchHashTagPage from "./views/page/SearchHashTagPage/SearchHashTagPage";
import ProfilePage from "./views/page/ProfilePage/ProfilePage";
import ChangePwPage from "./views/page/ChangePwPage/ChangePwPage";

// components
import SideBar from "./views/components/common/Sidebar/SideBar";
import NavBar from "./views/components/common/NavBar/NabBar";
import PostDetailPage from "./views/page/PostDetailPage/PostDetailPage";
import UserInfoUpdatePage from "./views/page/UserInfoUpdatePage/UserInfoUpdatePage";

function App(): JSX.Element {
  return (
    <div className={styles.app}>
      <NavBar />
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signin" element={<SignInPage />} />
        <Route path="/profile/:nickname" element={<ProfilePage />} />
        <Route path="/upload-post" element={<UploadPostPage />} />
        <Route path="/post/:postId" element={<PostDetailPage />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/search/:tag" element={<SearchHashTagPage />} />
        <Route
          path="/update-userinfo/:nickname"
          element={<UserInfoUpdatePage />}
        />
        <Route path="/change-password" element={<ChangePwPage />} />
      </Routes>
      <SideBar />
    </div>
  );
}

export default App;
