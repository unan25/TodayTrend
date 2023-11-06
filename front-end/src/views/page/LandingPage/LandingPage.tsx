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

function LandingPage() {
  const dispatch = useDispatch<any>();

  useEffect(() => {
    dispatch(auth_client());
  }, []);

  return (
    <Container className="mx-0 px-0">
      <Row>
        <Col md={4} className="px-0">
          <SideBar />
        </Col>
        <Col md={8}></Col>
      </Row>
    </Container>
  );
}

export default LandingPage;
