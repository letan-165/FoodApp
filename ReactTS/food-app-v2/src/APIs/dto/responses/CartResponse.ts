import { ItemResponse } from "./ItemResponse";

export interface CartResponse {
  userID: string;
  restaurants: CartRestaurantResponse[];
}

export interface CartRestaurantResponse {
  restaurantID: string;
  name: string;
  menu: ItemResponse[];
}
