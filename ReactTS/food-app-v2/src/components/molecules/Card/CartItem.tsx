import { useContext, useEffect, useState } from "react";
import { Box, Checkbox, IconButton, Stack, Typography } from "@mui/material";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import { ItemResponse } from "../../../APIs/dto/responses/ItemResponse";
import { CartContext } from "../../../hooks/TotalCart";

const CartItem = ({
  item,
  restaurantID,
  checked,
  onChangeChecked,
}: {
  item: ItemResponse;
  restaurantID: string;
  checked: boolean;
  onChangeChecked: (checkedChange: boolean) => void;
}) => {
  const [updateItem, setUpdateItem] = useState<ItemResponse>(item);
  const [check, setCheck] = useState<boolean>(checked);
  const { dispatch } = useContext(CartContext);

  useEffect(() => {
    setCheck(checked);
  }, [checked]);

  useEffect(() => {
    dispatch({ checked: check, restaurantID, item: updateItem });
  }, [check, updateItem]);

  return (
    <Stack
      direction={"row"}
      sx={{
        margin: "10px 10px 0 10px",
        borderBottom: "solid 1px grey",
      }}
    >
      <Box
        component={"img"}
        src="https://png.pngtree.com/png-clipart/20230504/original/pngtree-3d-food-burger-multi-layered-colorful-three-dimensional-effect-png-image_9136644.png"
        sx={{ width: 80, height: 80 }}
      />
      <Box>
        <Typography sx={{ fontWeight: "bold", fontSize: 20 }}>
          {item.name}
        </Typography>
        <Typography sx={{}}>{`${item.price}đ`}</Typography>
        <Stack direction={"row"} alignItems={"center"}>
          <IconButton
            onClick={() => {
              setUpdateItem((item) => ({
                ...item,
                quantity: item.quantity + 1,
              }));
            }}
          >
            <AddCircleOutlineIcon />
          </IconButton>
          <Typography sx={{ fontWeight: "bold" }}>
            {updateItem.quantity}
          </Typography>
          <IconButton
            onClick={() => {
              setUpdateItem((item) => ({
                ...item,
                quantity: Math.max(1, item.quantity - 1),
              }));
            }}
          >
            <RemoveCircleOutlineIcon />
          </IconButton>
        </Stack>
      </Box>
      <Stack
        sx={{
          marginLeft: "auto",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Checkbox
          checked={checked}
          onChange={(e) => {
            onChangeChecked(e.target.checked);
            setCheck(e.target.checked);
          }}
        />
        <Typography sx={{ fontWeight: "bold", fontSize: 20 }}>
          {`${updateItem.quantity * updateItem.price}đ`}
        </Typography>
      </Stack>
    </Stack>
  );
};
export default CartItem;
