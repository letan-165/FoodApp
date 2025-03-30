export interface RestaurantRequest {
  userID: string;
  name: string;
  address: string;
  phone: string;
  status: string;
}

export interface ItemRequest {
  name: string;
  price: number;
  quantity: number;
}
