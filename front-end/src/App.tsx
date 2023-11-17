import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

// css
import styles from "./App.module.css";

// pages
import LandingPage from "./views/page/LandingPage/LandingPage";
import CreateAccountPage from "./views/page/CreateAccountPage/CreateAccountPage";
import CreateUserInfoPage from "./views/page/CreateUserInfo/CreateUserInfoPage";
import SignInPage from "./views/page/SignInPage/SignInPage";
import UploadPostPage from "./views/page/UploadPostPage/UploadPostPage";
import SearchPage from "./views/page/SearchPage/SearchPage";
import ProfilePage from "./views/page/ProfilePage/ProfilePage";

// components
import SideBar from "./views/components/Sidebar/SideBar";
import NavBar from "./views/components/NavBar/NabBar";
import PostDetailPage from "./views/page/PostDetailPage/PostDetailPage";

function App(): JSX.Element {
  return (
    <div className={styles.app}>
      <NavBar />
      <Router>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/create-account" element={<CreateAccountPage />} />
          <Route path="/create-userinfo" element={<CreateUserInfoPage />} />
          <Route path="/upload-post" element={<UploadPostPage />} />
          <Route path="/signin" element={<SignInPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/profile/:userId" element={<ProfilePage />} />
          <Route path="/post/:postId" element={<PostDetailPage />} />
        </Routes>
      </Router>
      <SideBar />
    </div>
  );
}

export default App;
