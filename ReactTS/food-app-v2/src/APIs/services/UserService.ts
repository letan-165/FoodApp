import axiosClient from "../axiosClient";
import { ApiResponse } from "../dto/ApiResponse";
import {
  UserSaveRequest,
  UserUpdateRequest,
} from "../dto/requests/UserRequest";
import { UserResponse } from "../dto/responses/UserResponse";
const API_USER = "account_service/user";

const UserService = {
  getAll: async () => {
    return await axiosClient.get(API_USER);
  },

  save: async (request: UserSaveRequest) => {
    try {
      await axiosClient.post(API_USER, request);
      alert("Đăng kí thành công");
      return true;
    } catch (e: any) {
      if (e.code === 1002) {
        alert("Người dùng đã tồn tại");
      } else {
        ("Lỗi hệ thống");
      }
      return false;
    }
  },

  findById: async (userID: string): Promise<UserResponse> => {
    const data: ApiResponse = await axiosClient.post(
      `${API_USER}/id=${userID}`
    );
    return data.result;
  },
  findByName: (name: string) => {
    return axiosClient.post(`${API_USER}/name=${name}`);
  },
  update: (userID: string, request: UserUpdateRequest) => {
    return axiosClient.put(`${API_USER}/${userID}`, request);
  },
  delete: (userID: string) => {
    return axiosClient.delete(`${API_USER}/${userID}`);
  },
};

export default UserService;
