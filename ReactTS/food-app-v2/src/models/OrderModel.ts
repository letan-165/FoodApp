import { ItemRequest } from "../APIs/dto/requests/RestaurantRequest";

class OrderModel {
  constructor(
    public orderID: string,
    public customerID: string,
    public restaurantID: string,
    public shipperID: string,
    public menu: ItemRequest[],
    public total: number,
    public time: Date,
    public status: string,
    address: string,
    phone: string
  ) {}

  get formattedTime(): string {
    return this.time.toLocaleString("vi-VN", {
      day: "numeric",
      month: "numeric",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
      hour12: true,
    });
  }
}
export default OrderModel;
