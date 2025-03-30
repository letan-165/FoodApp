export interface UserSaveRequest {
  name: string;
  password: string;
  phone: string;
  gmail: string;
}

export interface UserUpdateRequest {
  name: string;
  password: string;
  phone: string;
  gmail: string;
  roles: string[];
}
