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

type UUID = {
  UUID_temp?: string;
  UUID?: string;
};

export { Account, UserInfo, UUID };
