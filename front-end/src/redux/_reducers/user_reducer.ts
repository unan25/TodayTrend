// redux
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  createAccount,
  signInUser,
  auth_client,
  logOut,
} from "../_actions/user_action";
import { Account, UUID } from "../../interface/UserInterface";

const initialState = {};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(
        createAccount.fulfilled,
        (state, action: PayloadAction<UUID>) => {
          return { ...state, ...action.payload };
        }
      )
      .addCase(createAccount.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      })
      .addCase(signInUser.fulfilled, (state, action: PayloadAction<UUID>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(signInUser.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      })
      .addCase(logOut.fulfilled, (state, action: PayloadAction<UUID>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(logOut.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      })
      .addCase(auth_client.fulfilled, (state, action: PayloadAction<UUID>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(auth_client.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
        };
      });
  },
});

export default userSlice.reducer;
