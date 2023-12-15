// redux
import { createAsyncThunk } from "@reduxjs/toolkit";

// axios
import axios from "axios";
import {
  Account,
  CurrentUser,
  SocialUser,
} from "../../interface/UserInterface";

// action types
const CREATE_ACCOUNT_USER = "user/createAccount";
const SIGN_IN_SOCIALUSER = "user/signInSocialUser";
const UPDATE_USERINFO_USER = "user/updateUserInfo";

const SIGN_IN_USER = "user/signInUser";
const LOG_OUT_USER = "user/logOut";
const AUTH_USER = "user/authUser";
//

export const createAccount = createAsyncThunk(
  CREATE_ACCOUNT_USER,
  async (account: Account, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/auth/signup", account);
      return {
        UUID_temp: response.data.uuid,
        userType: response.data.userType,
      };
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const updateUserInfo = createAsyncThunk(
  UPDATE_USERINFO_USER,
  async (userInfo: CurrentUser, { rejectWithValue }) => {
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
  async (account: Account, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/auth/login", account);

      return {
        UUID: response.data.uuid,
        userType: response.data.userType,
        role: response.data.role,
      };
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const signInSocialUser = createAsyncThunk(
  SIGN_IN_SOCIALUSER,
  async (socialUser: SocialUser, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/auth/social-login", socialUser);
      return {
        UUID: response.data.uuid,
        userType: response.data.userType,
        role: response.data.role,
        email: socialUser.email,
      };
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const logOut = createAsyncThunk(
  LOG_OUT_USER,
  async (uuid: CurrentUser, { rejectWithValue }) => {
    try {
      await axios.post("/api/auth/logout", uuid);
      return { UUID: "" };
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
