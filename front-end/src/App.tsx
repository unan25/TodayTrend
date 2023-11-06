import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import LandingPage from "./views/page/LandingPage/LandingPage";
import SignUpPage from "./views/page/SignUpPage/SignUpPage";
import SignInPage from "./views/page/SignInPage/SignInPage";

function App(): JSX.Element {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signin" element={<SignInPage />} />
      </Routes>
    </Router>
  );
}

export default App;
