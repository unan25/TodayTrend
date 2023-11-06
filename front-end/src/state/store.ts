import { configureStore, getDefaultMiddleware } from "@reduxjs/toolkit";
import UserReducer from "./_reducers/user_reducer";
import { ToolkitStore } from "@reduxjs/toolkit/dist/configureStore";

const store: ToolkitStore = configureStore({
  reducer: {
    user: UserReducer,
  },
  middleware: [...getDefaultMiddleware()],
  devTools: process.env.NODE_ENV !== "production",
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;

export default store;
