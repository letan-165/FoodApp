import { Stack } from "@mui/material";
import TextCustom from "../../atoms/TextCustom";
import { OrderResponse } from "../../../APIs/dto/responses/OrderResponse";

const CardOrder = ({ order }: { order: OrderResponse }) => {
  return (
    <Stack
      direction={"row"}
      justifyContent={"space-between"}
      alignItems={"center"}
      padding={"10px 20px 0 20px"}
      borderBottom={"solid 1px"}
    >
      <Stack>
        <TextCustom title="Mã đơn: " text={order.orderID} />
        <TextCustom title="Trạng thái thanh toán: " text={order.status} />
        <TextCustom title="Tổng giá tiền" text={`${order.total}đ`} />
      </Stack>
      <TextCustom title="Thời gian đặt" text={`${order.time}`} />
    </Stack>
  );
};

export default CardOrder;
