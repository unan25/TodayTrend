// redux
import { createAsyncThunk } from "@reduxjs/toolkit";

// axios
import axios, { AxiosError } from "axios";
import { Account, UserInfo } from "interface/UserInterface";

// action types
const CREATE_ACCOUNT_USER = "user/createAccount";
const UPDATE_USERINFO_USER = "user/updateUserInfo";
const SIGN_IN_USER = "user/signInUser";
const AUTH_USER = "user/authUser";
//

export const createAccount = createAsyncThunk(
  CREATE_ACCOUNT_USER,
  async (account: Account, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/auth/signup", account);
      return { UUID: response.data };
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const updateUserInfo = createAsyncThunk(
  UPDATE_USERINFO_USER,
  async (userInfo: UserInfo, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/user/signup", userInfo);
      return { UUID: response.data.uuid };
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const signInUser = createAsyncThunk(
  SIGN_IN_USER,
  async (account: Account, { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/users/signin", account);
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
