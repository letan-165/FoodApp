import { Stack, Typography } from "@mui/material";
import { useOrderAPI } from "../../../hooks/useAPI/useOrderAPI";
import { useContext, useEffect } from "react";
import { PageContext } from "../../../hooks/ChangePage";
import CardRightOrder from "../../../components/molecules/Card/CardRightOrder";

const RestaurantOrders = () => {
  const { setData, setPageLeft } = useContext(PageContext);
  const { data } = useContext(PageContext);
  const { ordersRestaurant, refetchRestaurant } = useOrderAPI({
    restaurantID: data.restaurantID,
  });
  useEffect(() => {
    if (data.restaurantID) {
      refetchRestaurant();
    }
  }, [data.restaurantID, refetchRestaurant]);
  return (
    <Stack justifyContent={"center"} gap={1}>
      <Typography variant="h5" sx={{ fontWeight: "bold" }}>
        Đơn đặt hàng --- Còn lại: {ordersRestaurant?.length}
      </Typography>
      {ordersRestaurant &&
        ordersRestaurant.map((order) => (
          <CardRightOrder
            key={order.orderID}
            order={order}
            typeRestaurant={true}
            onClick={() => {
              setData((data: Record<string, any>) => ({
                ...data,
                order: order,
              }));
              setPageLeft("OrdersRestaurant");
            }}
          />
        ))}
    </Stack>
  );
};

export default RestaurantOrders;
