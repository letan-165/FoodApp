import { Box, Typography, Stack } from "@mui/material";
import CardPaymentItem from "./CardPaymentItem";
import RestaurantModel from "../../../models/RestaurantModel";
import { CartRestaurantResponse } from "../../../APIs/dto/responses/CartResponse";

const CardPaymentRestaurant = ({
  index,
  restaurant,
}: {
  index: number;
  restaurant: CartRestaurantResponse;
}) => {
  return (
    <Box>
      <Stack direction={"row"}>
        <Typography
          sx={{
            flex: 1,
            textOverflow: "ellipsis",
            whiteSpace: "nowrap",
            overflow: "hidden",
            paddingTop: 1,
            fontSize: 20,
            fontWeight: "bold",
          }}
        >
          {`*${index}.
          ${restaurant.name}`}
        </Typography>
      </Stack>
      <Box>
        {restaurant.menu.map((item) => (
          <CardPaymentItem key={item.itemID} item={item} />
        ))}
      </Box>
    </Box>
  );
};

export default CardPaymentRestaurant;
