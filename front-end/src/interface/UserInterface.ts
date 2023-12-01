type Account = {
  email: string;
  password: string;
  confirmPassword?: string;
};

type SocialUser = {
  email: string;
  UUID?: string;
};

type UserInfo = {
  uuid?: string;
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

export { Account, UserInfo, CurrentUser, SocialUser };
