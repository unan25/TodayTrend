// react
import React, { useEffect } from "react";

// redux
import { useDispatch, useSelector } from "react-redux";
import { auth_client } from "../../../redux/_actions/user_action";

// styles
import styles from "./LandingPage.module.css";

function LandingPage() {
  return <div className="page-body">시작 페이지</div>;
}

export default LandingPage;
