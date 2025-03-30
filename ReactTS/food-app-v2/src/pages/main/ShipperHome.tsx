import { Box, Stack, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import ButtonCustom from "../../components/atoms/ButtonCustom";
import CardLeftOrder from "../../components/molecules/Card/CardRightOrder";
import { OrderResponse } from "../../APIs/dto/responses/OrderResponse";
import { useOrderAPI } from "../../hooks/useAPI/useOrderAPI";
import LoadingCustom from "../../components/atoms/LoadingCustom";
import { ChangePage, PageContext } from "../../hooks/ChangePage";
import TextHeader from "../../components/atoms/TextHeader";
import CardOrderShipper from "../../components/molecules/Card/CardOrderShipper";

const ShipperHome = () => {
  const { ordersConfirmed } = useOrderAPI({});

  return ordersConfirmed ? (
    <ChangePage>
      <LeftBody />
      <RightBody orders={ordersConfirmed} />
    </ChangePage>
  ) : (
    <LoadingCustom />
  );
};

export default ShipperHome;

const LeftBody = () => {
  const { data } = useContext(PageContext);
  const { getOrder, refetchOrder } = useOrderAPI({ orderID: data.orderID });
  const { orderShipper } = useOrderAPI({});
  const [orders, setOrders] = useState<OrderResponse[] | undefined>(undefined);
  const listTitle = ["Đơn đang giao", "Đơn đã giao"];

  const [clicks, setClicks] = useState<boolean[]>(
    new Array(listTitle.length)
      .fill(false)
      .map((click, i) => (i === 0 ? true : click))
  );
  useEffect(() => {
    if (data.orderID) {
      refetchOrder();
    }
  }, [data.orderID, refetchOrder]);

  useEffect(() => {
    const status = clicks[0] ? "ON_DELIVERY" : "DELIVERED";
    setOrders(orderShipper?.filter((order) => order.status === status));
  }, [clicks, orderShipper, getOrder]);

  console.log("LeftBody", getOrder);
  return (
    <Box sx={{ width: "65%", minHeight: "90vh" }}>
      <Typography sx={{ fontSize: 30, fontWeight: "bold" }}>
        Tìm đơn hàng
      </Typography>
      {getOrder ? (
        <CardOrderShipper order={getOrder} />
      ) : (
        <Typography sx={{ fontSize: 30 }}>Vui lòng chọn đơn hàng</Typography>
      )}

      <Stack direction={"row"} justifyContent={"space-between"}>
        {listTitle.map((title, index) => (
          <TextHeader
            key={index}
            text={title}
            click={clicks[index]}
            onChange={() =>
              setClicks(clicks.map((_, i) => (index === i ? true : false)))
            }
          />
        ))}
      </Stack>
      {orders?.map((order) => (
        <CardOrderShipper key={order.orderID} order={order} />
      ))}
      {/* <Typography sx={{ fontSize: 30, fontWeight: "bold" }}>
        Thực đơn
      </Typography>
      <Grid2 container spacing={3}>
        {getOrder.menu.map((item) => (
          <CardItem
            key={item.itemID}
            item={item}
            restaurantID=""
            restaurantName=""
          />
        ))}
      </Grid2> */}
    </Box>
  );
};
const RightBody = ({ orders }: { orders: OrderResponse[] }) => {
  const { setData } = useContext(PageContext);
  return (
    <Stack sx={{ width: "35%", minHeight: "90vh", paddingLeft: 4, gap: 2 }}>
      <Typography
        sx={{ fontSize: 30, fontWeight: "bold", textAlign: "center" }}
      >
        Đơn hàng trống
      </Typography>
      <Box width={"30%"}>
        <ButtonCustom text="Tìm nhanh" borderRadius={2} />
      </Box>
      {orders.map((order) => (
        <CardLeftOrder
          key={order.orderID}
          order={order}
          onClick={() => {
            setData((d: Record<string, any>) => ({
              ...d,
              orderID: order.orderID,
            }));
          }}
        />
      ))}
    </Stack>
  );
};
