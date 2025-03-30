import { useState } from "react";
import { Box, Typography, Checkbox, Stack } from "@mui/material";
import { CartRestaurantResponse } from "../../../APIs/dto/responses/CartResponse";
import CartItem from "./CartItem";

const CartShop = ({
  index,
  restaurant,
}: {
  index: number;
  restaurant: CartRestaurantResponse;
}) => {
  const [listCheck, setListCheck] = useState<boolean[]>(
    new Array(restaurant.menu.length).fill(false)
  );
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
        <Checkbox
          checked={listCheck.every((check) => check)}
          indeterminate={
            listCheck.some((check) => check) &&
            !listCheck.every((check) => check)
          }
          onChange={(e) =>
            setListCheck(
              new Array(restaurant.menu.length).fill(e.target.checked)
            )
          }
        />
      </Stack>
      <Box>
        {restaurant.menu.map((item, i) => (
          <CartItem
            key={item.itemID}
            restaurantID={restaurant.restaurantID}
            item={item}
            checked={listCheck[i]}
            onChangeChecked={(e) => {
              const newList = [...listCheck];
              newList[i] = e;
              setListCheck(newList);
            }}
          />
        ))}
      </Box>
    </Box>
  );
};

export default CartShop;
