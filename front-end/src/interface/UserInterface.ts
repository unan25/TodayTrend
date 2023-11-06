type UserInfo = {
  email: string;
  password?: string;
  lastname?: string;
  name?: string;
};

type SignUp = {
  email: string;
  password: string;
  lastname: string;
  name: string;
};

type SignIn = {
  email: string;
  password: string;
};

export { SignUp, SignIn, UserInfo };
