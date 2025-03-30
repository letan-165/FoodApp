export interface Role {
  name: string;
  description: string;
  permissions: Permission[];
}
export interface Permission {
  name: string;
  description: string;
}

export interface UserResponse {
  userID: string;
  name: string;
  password: string;
  phone: string;
  gmail: string;
  roles: Role[];
}
