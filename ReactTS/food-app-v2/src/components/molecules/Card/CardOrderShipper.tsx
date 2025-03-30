import { Stack, Box, Typography } from "@mui/material";
import { OrderResponse } from "../../../APIs/dto/responses/OrderResponse";
import { useOrderAPI } from "../../../hooks/useAPI/useOrderAPI";
import ButtonCustom from "../../atoms/ButtonCustom";

const CardOrderShipper = ({ order }: { order: OrderResponse }) => {
  const { updateStatus } = useOrderAPI({});
  return (
    <Stack
      direction={"row"}
      alignItems={"center"}
      justifyContent={"space-between"}
      padding={"0 20px 20px 20px"}
      bgcolor={"#F9F9F9"}
      boxShadow={1}
    >
      <Box>
        <Typography sx={{ fontSize: 30 }}>
          <span style={{ fontWeight: "bold" }}>Đơn hàng: </span> {order.orderID}
        </Typography>
        <Typography sx={{ fontSize: 20 }}>
          Từ {order.restaurantID} đến {order.address}
        </Typography>
      </Box>
      {order.status !== "DELIVERED" && (
        <Stack spacing={1}>
          <ButtonCustom
            text={order.status === "ON_DELIVERY" ? "Hủy" : "Chấp nhận"}
            color={order.status === "ON_DELIVERY" ? "black" : undefined}
            backgroundColor={order.status === "ON_DELIVERY" ? "red" : undefined}
            borderRadius={3}
            onClick={() => {
              updateStatus.mutate({
                orderID: order.orderID,
                request: {
                  shipperID:
                    order.status === "ON_DELIVERY"
                      ? null
                      : localStorage.getItem("userID") || "",
                  status:
                    order.status === "ON_DELIVERY"
                      ? "CONFIRMED"
                      : "ON_DELIVERY",
                },
              });
            }}
          />
          {order.status === "ON_DELIVERY" && (
            <ButtonCustom
              text="Hoàn thành"
              backgroundColor="green"
              borderRadius={3}
              onClick={() => {
                updateStatus.mutate(
                  {
                    orderID: order.orderID,
                    request: {
                      shipperID:
                        order.status === "ON_DELIVERY"
                          ? null
                          : localStorage.getItem("userID") || "",
                      status: "DELIVERED",
                    },
                  },
                  {
                    onSuccess: () => console.log("Hoàn thành đơn "),
                    onError: (e) => console.error(e),
                  }
                );
              }}
            />
          )}
        </Stack>
      )}
    </Stack>
  );
};

export default CardOrderShipper;
