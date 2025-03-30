import { Box, Stack, Typography } from "@mui/material";
import CardPaymentRestaurant from "../../../components/molecules/Card/CardPaymentRestaurant";
import { useContext } from "react";
import { CartContext } from "../../../hooks/TotalCart";
import { useCartAPI } from "../../../hooks/useAPI/useCartAPI";
import LoadingCustom from "../../../components/atoms/LoadingCustom";
import { CartRestaurantResponse } from "../../../APIs/dto/responses/CartResponse";

const PaymentPage = () => {
  const { state } = useContext(CartContext);
  const { carts } = useCartAPI();
  const total = Object.values(state)
    .flatMap((item) => item)
    .reduce((sum, item) => sum + item.price * item.quantity, 0);

  return (
    <Box>
      <Typography variant="h5" sx={{ fontWeight: "bold" }}>
        Món ăn
      </Typography>
      {/* LIST */}
      <Box sx={{ maxHeight: 500, overflowY: "auto" }}>
        {state ? (
          Object.entries(state).map(([restaurantID, itemsResponse], index) => {
            let cartRestaurantResponse: CartRestaurantResponse | undefined =
              carts?.restaurants?.find(
                (res) => res.restaurantID === restaurantID
              );

            if (cartRestaurantResponse) {
              cartRestaurantResponse.menu = itemsResponse;
              return (
                <CardPaymentRestaurant
                  key={index}
                  index={index + 1}
                  restaurant={cartRestaurantResponse}
                />
              );
            }
          })
        ) : (
          <LoadingCustom />
        )}
      </Box>
      <Stack
        direction={"row"}
        justifyContent={"space-between"}
        alignItems={"center"}
        padding={"20px 10px 0 0"}
      >
        <Typography sx={{ width: "100%", fontSize: 30, fontWeight: "bold" }}>
          Tổng: {total}đ
        </Typography>
      </Stack>
    </Box>
  );
};

export default PaymentPage;
