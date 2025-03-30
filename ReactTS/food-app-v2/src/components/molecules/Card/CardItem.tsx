import {
  Box,
  Card,
  CardMedia,
  Typography,
  IconButton,
  Stack,
} from "@mui/material";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import { ItemResponse } from "../../../APIs/dto/responses/ItemResponse";
import { useContext } from "react";
import { PageContext } from "../../../hooks/ChangePage";
import { useCartAPI } from "../../../hooks/useAPI/useCartAPI";
const CardItem = ({
  restaurantID,
  restaurantName,
  item,
  permission = false,
}: {
  permission?: boolean;
  restaurantID: string;
  restaurantName: string;
  item: ItemResponse;
}) => {
  const { setData, setPageRight } = useContext(PageContext);
  const { addItem } = useCartAPI();

  return (
    <Card
      sx={{
        height: 200,
        padding: "10px 10px 20px 10px",
        borderRadius: 1,
        boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.2)",
        cursor: "pointer",
      }}
      onClick={() => {
        if (permission) {
          setData((data: Record<string, any>) => ({
            ...data,
            item: { restaurantID, itemID: item.itemID },
          }));
          setPageRight("FormEditMenu");
        }
      }}
    >
      <Box
        sx={{
          position: "relative",
          width: "100%",
          height: "75%",
        }}
      >
        <Box
          sx={{
            position: "absolute",
            width: "50%",
            left: 10,
            top: 10,
            borderRadius: 1,
            bgcolor: "white",
            padding: "0 10px",
          }}
        >
          <Typography
            sx={{
              maxWidth: "100%",
              textOverflow: "ellipsis",
              whiteSpace: "nowrap",
              overflow: "hidden",
            }}
          >
            {restaurantName}
          </Typography>
        </Box>

        <CardMedia
          component="img"
          image="https://tse1.mm.bing.net/th?id=OIP.r0X-CYZvvYM71ZdNU4LXQQHaE8&pid=Api&P=0&h=220"
          alt="food"
          sx={{ borderRadius: 2, height: "100%" }}
        />
      </Box>

      <Stack
        direction={"row"}
        justifyContent={"space-between"}
        alignItems={"center"}
      >
        <Stack>
          <Typography variant="h6">{item.name}</Typography>
          <Typography variant="h6">{`${item.price}đ`}</Typography>
        </Stack>
        {permission ? (
          <Typography variant="h6">{`x${item.quantity}`}</Typography>
        ) : (
          <IconButton
            onClick={async () =>
              addItem.mutate(
                {
                  cartID: localStorage.getItem("userID") || "",
                  restaurantID,
                  itemID: item.itemID,
                },
                {
                  onSuccess: () => console.log("Thêm giỏ hàng thành công"),
                  onError: () => alert("Món ăn đã tồn tại trong giỏ hàng"),
                }
              )
            }
          >
            <AddCircleOutlineIcon sx={{ fontSize: 40 }} />
          </IconButton>
        )}
      </Stack>
    </Card>
  );
};

export default CardItem;
