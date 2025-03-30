import { ItemResponse } from "./ItemResponse";

export interface OrderResponse {
  orderID: string;
  customerID: string;
  restaurantID: string;
  shipperID: string | null;
  menu:ItemResponse[]
  total: number;
  time: string;
  status: string;
  address: string;
  phone: string;
}
