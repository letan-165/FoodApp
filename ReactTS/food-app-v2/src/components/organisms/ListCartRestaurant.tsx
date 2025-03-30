import { useContext, useState } from "react";
import { Button, Grid2, IconButton, Stack, Typography } from "@mui/material";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import AddIcon from "@mui/icons-material/Add";
import CardRestaurant, { cardBox } from "../molecules/Card/CardRestaurant";
import { PageContext } from "../../hooks/ChangePage";
import LoadingCustom from "../atoms/LoadingCustom";
import { useRestaurantAPI } from "../../hooks/useAPI/useRestaurantAPI";
import RestaurantModel from "../../models/RestaurantModel";

function ListCardRestaurant({ permission = false }: { permission?: boolean }) {
  const [clickMore, setClickMore] = useState<boolean>(false);
  const { setPageRight } = useContext(PageContext);
  const { restaurants, restaurantByUser } = useRestaurantAPI();
  const listRestaurant = permission ? restaurantByUser : restaurants;
  return listRestaurant ? (
    <>
      <Stack direction={"row"} alignItems={"center"} gap={2}>
        <Typography variant="h5" sx={{ fontWeight: "bold" }}>
          Cửa hàng
        </Typography>
        <Button
          onClick={() => setClickMore(!clickMore)}
          variant="contained"
          sx={{
            backgroundColor: "white",
            width: "10%",
            height: 20,
            color: "black",
            fontSize: 12,
            borderRadius: 2,
            border: "solid 1px",
            paddingTop: 1,
            marginRight: 5,
          }}
        >
          {clickMore ? "Thu" : "Thêm"}
          <ArrowForwardIcon sx={{ fontSize: 13 }} />
        </Button>
      </Stack>
      {/* LIST */}

      <Stack direction={"row"} alignItems={"center"}>
        <Grid2 container spacing={1} sx={{ gap: 2, paddingTop: 1 }}>
          {listRestaurant
            .filter((_: any, index: number) => index < 8 || clickMore)
            .map((restaurant: RestaurantModel) => (
              <CardRestaurant
                key={restaurant.restaurantID}
                restaurant={restaurant}
              />
            ))}
          {permission && (
            <IconButton
              sx={cardBox}
              onClick={() => setPageRight("FormAddRestaurant")}
            >
              <AddIcon sx={{ fontSize: 50 }} />
            </IconButton>
          )}
        </Grid2>
      </Stack>
    </>
  ) : (
    <LoadingCustom />
  );
}

export default ListCardRestaurant;
