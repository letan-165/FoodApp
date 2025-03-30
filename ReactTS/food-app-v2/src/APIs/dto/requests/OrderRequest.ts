export interface OrderSaveFromCartRequest {
  customerID: string;
  restaurantID: string;
  menu: ItemSaveOrderRequest[];
  address: string;
  phone: string;
}

export interface ItemSaveOrderRequest {
  itemID: number;
  quantity: number;
}

export interface UpdateStatusRequest {
  shipperID: string | null;
  status: string;
}
