import { ItemResponse } from "../APIs/dto/responses/ItemResponse";

class RestaurantModel {
  constructor(
    public restaurantID: string,
    public userID: string,
    public name: string,
    public menu: ItemResponse[],
    public address: string,
    public phone: string,
    public status: string
  ) {}
}
export default RestaurantModel;
