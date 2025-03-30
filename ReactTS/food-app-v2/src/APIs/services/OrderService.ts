import axiosClient from "../axiosClient";
import { ApiResponse } from "../dto/ApiResponse";
import {
  OrderSaveFromCartRequest,
  UpdateStatusRequest,
} from "../dto/requests/OrderRequest";
import { OrderResponse } from "../dto/responses/OrderResponse";

const API_ORDER = "/order_service/order";
const OrderService = {
  getAll: async (): Promise<OrderResponse[]> => {
    const data: ApiResponse = await axiosClient.get(API_ORDER);
    if (data.code === 1000) {
      return data.result;
    }
    return [];
  },
  getAllByUser: async (): Promise<OrderResponse[]> => {
    const userID = localStorage.getItem("userID") || "";
    const data: ApiResponse = await axiosClient.get(
      `${API_ORDER}/user/${userID}`
    );
    if (data.code === 1000) {
      return data.result;
    }
    return [];
  },
  getAllByRestaurant: async (
    restaurantID: string
  ): Promise<OrderResponse[]> => {
    const data: ApiResponse = await axiosClient.get(
      `${API_ORDER}/restaurant/${restaurantID}`
    );
    if (data.code === 1000) {
      return data.result;
    }
    return [];
  },
  getAllByShipper: async (): Promise<OrderResponse[]> => {
    const shipperID = localStorage.getItem("userID") || "";
    const data: ApiResponse = await axiosClient.get(
      `${API_ORDER}/shipper/${shipperID}`
    );
    if (data.code === 1000) {
      return data.result;
    }
    return [];
  },

  getAllStatus: async (status: string): Promise<OrderResponse[]> => {
    const data: ApiResponse = await axiosClient.get(
      `${API_ORDER}/status/${status}`
    );
    if (data.code === 1000) {
      return data.result;
    }
    return [];
  },
  getOrder: async (orderID: string): Promise<OrderResponse | undefined> => {
    if (!orderID) return undefined;
    const data: ApiResponse = await axiosClient.get(`${API_ORDER}/${orderID}`);
    return data.result;
  },

  saveFromCart: async ({ request }: { request: OrderSaveFromCartRequest }) => {
    const data: ApiResponse = await axiosClient.post(
      `${API_ORDER}/cart`,
      request
    );

    return data.result;
  },
  updateStatus: async ({
    orderID,
    request,
  }: {
    orderID: string;
    request: UpdateStatusRequest;
  }) => {
    const data: ApiResponse = await axiosClient.put(
      `${API_ORDER}/status/${orderID}`,
      request
    );

    return data.result;
  },
};

export default OrderService;
