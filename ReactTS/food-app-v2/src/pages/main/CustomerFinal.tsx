import { Box, Stack } from "@mui/material";
import { useEffect, useState } from "react";
import CardOrder from "../../components/molecules/Card/CardOrder";
import TextHeader from "../../components/atoms/TextHeader";
import { useOrderAPI } from "../../hooks/useAPI/useOrderAPI";
import { OrderResponse } from "../../APIs/dto/responses/OrderResponse";
import { OrderStatus } from "../../APIs/dto/StatusAPI";

const CustomerFinal = () => {
  const { ordersUser } = useOrderAPI();
  const [order, setOrder] = useState<OrderResponse[] | null>(null);

  const listTitle = [
    "Tất cả",
    "Đã xác nhận",
    "Đợi nhận đơn",
    "Đang giao",
    "Đã giao",
    "Đã hủy",
  ];

  const [clicks, setClick] = useState<boolean[]>(
    new Array(listTitle.length)
      .fill(false)
      .map((click, i) => (i === 0 ? true : click))
  );

  useEffect(() => {
    if (ordersUser) {
      const indexTrue = clicks.indexOf(true);

      if (!indexTrue) {
        setOrder(ordersUser);
      } else {
        setOrder(
          ordersUser.filter((order) => order.status === OrderStatus[indexTrue])
        );
      }
    }
  }, [ordersUser, clicks]);

  return (
    <Box minHeight={"90vh"} width={"100%"}>
      <Stack direction={"row"} width={"100%"} justifyContent={"space-evenly"}>
        {listTitle.map((title, index) => (
          <TextHeader
            key={index}
            text={title}
            click={clicks[index]}
            onChange={() =>
              setClick(clicks.map((_, i) => (index === i ? true : false)))
            }
          />
        ))}
      </Stack>
      {order &&
        order.map((order) => <CardOrder key={order.orderID} order={order} />)}
    </Box>
  );
};

export default CustomerFinal;
