import { Box, Stack, Typography } from "@mui/material";
import ButtonCustom from "../../../components/atoms/ButtonCustom";
import CartShop from "../../../components/molecules/Card/CartShop";
import LoadingCustom from "../../../components/atoms/LoadingCustom";
import { useCartAPI } from "../../../hooks/useAPI/useCartAPI";
import { useContext } from "react";
import { CartContext } from "../../../hooks/TotalCart";
import { PageContext } from "../../../hooks/ChangePage";

const CartPage = () => {
  const { setPageLeft, setPageRight } = useContext(PageContext);
  const { carts } = useCartAPI();
  const { state } = useContext(CartContext);

  const total = Object.values(state)
    .flatMap((item) => item)
    .reduce((sum, item) => sum + item.price * item.quantity, 0);

  return (
    <Box>
      <Typography variant="h5" sx={{ fontWeight: "bold" }}>
        Giỏ hàng
      </Typography>
      {/* LIST */}
      <Box sx={{ maxHeight: 500, overflowY: "auto" }}>
        {carts?.restaurants ? (
          carts.restaurants
            .filter((restaurant) => restaurant.menu.length != 0)
            .map((restaurant, index) => (
              <CartShop
                key={index + 1}
                index={index + 1}
                restaurant={restaurant}
              />
            ))
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
        <Typography sx={{ width: "50%", fontSize: 30, fontWeight: "bold" }}>
          Tổng: {total}đ
        </Typography>
        <ButtonCustom
          text="Đặt hàng"
          fontSize={20}
          borderRadius={3}
          backgroundColor="#3f41c0"
          onClick={async () => {
            if (total === 0) {
              alert("Vui lòng chọn sản phẩm");
              return;
            }

            setPageLeft("InfoPayment");
            setPageRight("PaymentPage");
          }}
        />
      </Stack>
    </Box>
  );
};

export default CartPage;
