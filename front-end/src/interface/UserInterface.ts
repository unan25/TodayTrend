type Account = {
  email: string;
  password: string;
};

type UserInfo = {
  email?: string;
  name?: string;
  nickname?: string;
  phone?: string;
  gender?: string;
  birth?: string;
  website?: string;
  introduction?: string;
};

type CurrentUser = {
  UUID_temp?: string;
  UUID?: string;
  uuid?: string;
  accessToken?: string;
};

export { Account, UserInfo, CurrentUser };
