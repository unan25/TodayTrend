import { configureStore, getDefaultMiddleware } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import sessionStorage from "redux-persist/es/storage";
import UserReducer from "./_reducers/user_reducer";
import { ToolkitStore } from "@reduxjs/toolkit/dist/configureStore";

const userPersistConfig = {
  key: "user",
  storage: sessionStorage,
  whitelist: ["UUID", "userType", "role"],
};

const userPersistedReducer = persistReducer(userPersistConfig, UserReducer);

const store: ToolkitStore = configureStore({
  reducer: {
    user: userPersistedReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }),
  devTools: process.env.NODE_ENV !== "production",
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;

const persistor = persistStore(store);

export { store, persistor };
