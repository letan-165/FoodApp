import { Stack, IconButton, Typography, Grid2 } from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import CardItem from "../../components/molecules/Card/CardItem";
import { useContext } from "react";
import { PageContext } from "../../hooks/ChangePage";
import { ItemResponse } from "../../APIs/dto/responses/ItemResponse";
import LoadingCustom from "../../components/atoms/LoadingCustom";

const OrdersRestaurant = () => {
  const { setPageLeft, data } = useContext(PageContext);
  return data.order ? (
    <>
      <Stack direction={"row"} alignItems={"center"}>
        <IconButton onClick={() => setPageLeft("InfoRestaurant")}>
          <HomeIcon
            sx={{ fontSize: 50, color: "black", borderRight: "solid 1px" }}
          />
        </IconButton>
        <Typography variant="h5">
          <span style={{ fontWeight: "bold" }}>Thực đơn</span>:{" "}
          {data.order.orderID}
        </Typography>
      </Stack>
      <Grid2 container spacing={3}>
        {data.order.menu.map((item: ItemResponse) => (
          <CardItem
            key={item.itemID}
            item={item}
            restaurantName=""
            restaurantID=""
          />
        ))}
      </Grid2>
    </>
  ) : (
    <LoadingCustom />
  );
};

export default OrdersRestaurant;
