// redux
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  createAccount,
  signInUser,
  auth_client,
} from "../_actions/user_action";
import { Account, UUID } from "../../interface/UserInterface";

interface UserState {
  UUID?: UUID;
}

const initialState: UserState = {};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(
        createAccount.fulfilled,
        (state, action: PayloadAction<Account>) => {
          return { ...state, ...action.payload };
        }
      )
      .addCase(createAccount.rejected, (state, action: PayloadAction<any>) => {
        return {
          ...state,
          ...action.payload,
        };
      })
      .addCase(
        signInUser.fulfilled,
        (state, action: PayloadAction<Account>) => {
          return {
            ...state,
            ...action.payload,
          };
        }
      )
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
