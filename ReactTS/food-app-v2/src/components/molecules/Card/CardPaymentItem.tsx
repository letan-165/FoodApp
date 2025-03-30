import { Box, Stack, Typography } from "@mui/material";
import ItemModel from "../../../APIs/dto/responses/ItemResponse";

const CardPaymentItem = ({ item }: { item: ItemModel }) => {
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
        sx={{ width: 60, height: 60 }}
      />
      <Box>
        <Typography sx={{ fontWeight: "bold", fontSize: 20 }}>
          {item.name}
        </Typography>
        <Stack direction={"row"}>
          <Typography sx={{}}>{`${item.price}đ`}</Typography>
          <Typography sx={{ fontWeight: "bold", marginLeft: "50px" }}>
            x{item.quantity}
          </Typography>
        </Stack>
      </Box>
      <Stack
        sx={{
          marginLeft: "auto",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Typography sx={{ fontWeight: "bold", fontSize: 20 }}>
          {`${item.quantity * item.price}đ`}
        </Typography>
      </Stack>
    </Stack>
  );
};

export default CardPaymentItem;
