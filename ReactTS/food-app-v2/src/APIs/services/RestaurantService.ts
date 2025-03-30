import RestaurantModel from "../../models/RestaurantModel";
import axiosClient from "../axiosClient";
import { ApiResponse } from "../dto/ApiResponse";
import {
  ItemRequest,
  RestaurantRequest,
} from "../dto/requests/RestaurantRequest";
const API_RESTAURANT = "/order_service/restaurant";

const RestaurantService = {
  getAll: async (): Promise<RestaurantModel[]> => {
    const data: ApiResponse = await axiosClient.get(API_RESTAURANT);
    if (data.code === 1000) {
      return data.result;
    }
    return [];
  },
  getAllByUser: async (): Promise<RestaurantModel[]> => {
    const userID: string = localStorage.getItem("userID") || "";
    if (!userID) {
      return [];
    }
    const data: ApiResponse = await axiosClient.get(
      `${API_RESTAURANT}/user/id=${userID}`
    );

    if (data.code === 1000) {
      return data.result;
    }
    return [];
  },

  findRestaurant: async (restaurantID: string): Promise<RestaurantModel> => {
    const data: ApiResponse = await axiosClient.get(
      `${API_RESTAURANT}/${restaurantID}`
    );

    return data.result;
  },

  addRestaurant: async (request: RestaurantRequest) => {
    return await axiosClient.post(API_RESTAURANT, request);
  },
  updateRestaurant: async (
    restaurantID: string,
    request: RestaurantRequest
  ) => {
    return await axiosClient.put(`${API_RESTAURANT}/${restaurantID}`, request);
  },

  deleteRestaurant: async (restaurantID: string) => {
    return await axiosClient.delete(`${API_RESTAURANT}/${restaurantID}`);
  },

  addItem: async (restaurantID: string, request: ItemRequest) => {
    return await axiosClient.put(
      `${API_RESTAURANT}/id=${restaurantID}/item/add`,
      request
    );
  },
  updateItem: async (
    restaurantID: string,
    itemID: number,
    request: ItemRequest
  ) => {
    return await axiosClient.put(
      `${API_RESTAURANT}/id=${restaurantID}/item/id=${itemID}/edit`,
      request
    );
  },
  deleteItem: async (restaurantID: string, itemID: number) => {
    return await axiosClient.put(
      `${API_RESTAURANT}/id=${restaurantID}/item/id=${itemID}/delete`
    );
  },
};

export default RestaurantService;
