// redux
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  createAccount,
  signInUser,
  auth_client,
  logOut,
  signInSocialUser,
} from "../_actions/user_action";
import { CurrentUser } from "../../interface/UserInterface";

const initialState = {};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      // CreateAccount
      .addCase(
        createAccount.fulfilled,
        (state, action: PayloadAction<CurrentUser>) => {
          return { ...state, ...action.payload };
        }
      )
      .addCase(createAccount.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      })
      // SignInUser
      .addCase(
        signInUser.fulfilled,
        (state, action: PayloadAction<CurrentUser>) => {
          return {
            ...state,
            ...action.payload,
          };
        }
      )
      .addCase(signInUser.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      })
      // SignInSocialuser
      .addCase(
        signInSocialUser.fulfilled,
        (state, action: PayloadAction<CurrentUser>) => {
          return {
            ...state,
            ...action.payload,
          };
        }
      )
      .addCase(signInSocialUser.rejected, (state, action: any) => {
        return {
          ...state,
        };
      })
      // LogOut
      .addCase(
        logOut.fulfilled,
        (state, action: PayloadAction<CurrentUser>) => {
          return {
            ...action.payload,
          };
        }
      )
      .addCase(logOut.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      })
      // Auth
      .addCase(
        auth_client.fulfilled,
        (state, action: PayloadAction<CurrentUser>) => {
          return {
            ...state,
            ...action.payload,
          };
        }
      )
      .addCase(auth_client.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      });
  },
});

export default userSlice.reducer;
