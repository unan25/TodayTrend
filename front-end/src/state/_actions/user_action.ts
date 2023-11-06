// redux
import { AnyAction, AsyncThunk, createAsyncThunk } from "@reduxjs/toolkit";

// axios
import axios from "axios";
import { SignUp, UserInfo } from "interface/UserInterface";

// action types
const SIGN_UP_USER = "user/signUpUser";
const SIGN_IN_USER = "user/signInUser";
const AUTH_USER = "user/authUser";
//

export const signUpUser = createAsyncThunk(
  SIGN_UP_USER,
  async (userInfo: UserInfo, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/users/signup", userInfo);
      return response.data;
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const signInUser = createAsyncThunk(
  SIGN_IN_USER,
  async (userInfo: UserInfo, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/users/signin", userInfo);
      return response.data;
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const auth_client = createAsyncThunk(
  AUTH_USER,
  async (_, { rejectWithValue }) => {
    try {
      const response = await axios.get("/api/users/auth");
      return response.data;
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);
