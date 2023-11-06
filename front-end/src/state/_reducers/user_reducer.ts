// redux
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { signUpUser, signInUser, auth_client } from "../_actions/user_action";
import { SignIn, SignUp, UserInfo } from "../../interface/UserInterface";

interface UserState {
  currentUser?: UserInfo;
}

const initialState: UserState = {};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(signUpUser.fulfilled, (state, action: PayloadAction<SignUp>) => {
        return { ...state, ...action.payload };
      })
      .addCase(signUpUser.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(signInUser.fulfilled, (state, action: PayloadAction<SignIn>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(signInUser.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(auth_client.fulfilled, (state, action: PayloadAction<any>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(auth_client.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
          ...action.payload,
        };
      });
  },
});

export default userSlice.reducer;
