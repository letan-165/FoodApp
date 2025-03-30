import axiosClient from "../axiosClient";
import { ApiResponse } from "../dto/ApiResponse";
import { CartAddItemRequest } from "../dto/requests/CartRequest";
import { CartResponse } from "../dto/responses/CartResponse";

const API_CART = "/order_service/cart";
const CartService = {
  findCartUser: async (): Promise<CartResponse | null> => {
    const userID = localStorage.getItem("userID");
    if (!userID) {
      return null;
    }
    const data: ApiResponse = await axiosClient.get(`${API_CART}/${userID}`);
    return data.result;
  },

  addItem: async (request: CartAddItemRequest): Promise<CartResponse> => {
    const data: ApiResponse = await axiosClient.put(
      `${API_CART}/item/add`,
      request
    );
    return data.result;
  },
};
export default CartService;
