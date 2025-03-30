import axiosClient from "../axiosClient";
import { ApiResponse } from "../dto/ApiResponse";
import { LoginRequest, TokenRequest } from "../dto/requests/AuthRequest";

const API_AUTH = "account_service/auth";
const AuthService = {
  login: async (request: LoginRequest): Promise<boolean> => {
    try {
      const data: ApiResponse = await axiosClient.post(
        `${API_AUTH}/login`,
        request
      );

      localStorage.setItem("userName", request.username);
      localStorage.setItem("token", data.result);
      const userID = await AuthService.findUserID();
      localStorage.setItem("userID", userID);
      return true;
    } catch (e: any) {
      e.code != 1000 ? alert("Đăng nhập thất bại") : alert("Lỗi hệ thống");
      return false;
    }
  },
  logout: async (): Promise<boolean> => {
    const request: TokenRequest = {
      token: localStorage.getItem("token") || "",
    };
    try {
      const data: ApiResponse = await axiosClient.post(
        `${API_AUTH}/logout`,
        request
      );
      if (data.code === 1000) {
        localStorage.removeItem("userName");
        localStorage.removeItem("token");
        return true;
      }
      return false;
    } catch (e: any) {
      e.code != 1000 ? alert("Đăng xuất thất bại") : alert("Lỗi hệ thống");
      return false;
    }
  },

  instropect: async (): Promise<boolean> => {
    const request: TokenRequest = {
      token: localStorage.getItem("token") || "",
    };
    if (!request.token) {
      return false;
    }
    const data: ApiResponse = await axiosClient.post(
      `${API_AUTH}/instropect`,
      request
    );

    return data.code === 1000;
  },

  findUserID: async (): Promise<string> => {
    const request: TokenRequest = {
      token: localStorage.getItem("token") || "",
    };
    const data: ApiResponse = await axiosClient.post(
      `${API_AUTH}/find`,
      request
    );
    return data.result;
  },
};

export default AuthService;
