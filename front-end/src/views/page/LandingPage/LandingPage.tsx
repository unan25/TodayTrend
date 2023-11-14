// react
import React, { useEffect } from "react";

// redux
import { useDispatch, useSelector } from "react-redux";
import { auth_client } from "../../../state/_actions/user_action";

// react-bootstrap
import { Container, Row, Col } from "react-bootstrap";

// styles
import styles from "./LandingPage.module.css";

// components
import SideBar from "../../components/Sidebar/SideBar";

import { AppDispatch } from "../../../state/store";

import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

function LandingPage() {
  const dispatch = useDispatch<any>();

  useEffect(() => {
    dispatch(auth_client());
  }, []);

  return <div className="page-body">시작 페이지</div>;
}

export default LandingPage;
