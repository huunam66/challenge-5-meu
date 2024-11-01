export interface User {
  email: string;

  password: string;

  role: string;
}

export interface Grant {
  email: string;

  role: string;
}

export interface Authenticated {
  token: string,
  authentication: boolean;
}
