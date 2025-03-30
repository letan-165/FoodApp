import RestaurantModel from "../../models/RestaurantModel";
import { Box, Grid2, Stack, Typography, IconButton } from "@mui/material";
import SettingsIcon from "@mui/icons-material/Settings";
import ReplyAllIcon from "@mui/icons-material/ReplyAll";
import BorderColorIcon from "@mui/icons-material/BorderColor";
import ListAltIcon from "@mui/icons-material/ListAlt";
import ListCardRestaurant from "../../components/organisms/ListCartRestaurant";
import { useContext, useEffect, useState } from "react";
import { PageContext } from "../../hooks/ChangePage";
import CardItem from "../../components/molecules/Card/CardItem";
import { useRestaurantAPI } from "../../hooks/useAPI/useRestaurantAPI";
import LoadingCustom from "../../components/atoms/LoadingCustom";

const InfoRestaurant = ({ permission = false }: { permission?: boolean }) => {
  const { data } = useContext(PageContext);

  const { restaurants }: { restaurants: RestaurantModel[] | undefined } =
    useRestaurantAPI();

  const [restaurant, setRestaurant] = useState<RestaurantModel | undefined>(
    undefined
  );
  useEffect(() => {
    setRestaurant(
      restaurants?.find(
        (restaurant) => restaurant.restaurantID === data.restaurantID
      ) || restaurants?.[0]
    );
  });
  return (
    <Stack minHeight={"90vh"}>
      {permission && <ListCardRestaurant permission={permission} />}

      {restaurant ? (
        <>
          <Info restaurant={restaurant} permission={permission} />
          <Menu permission={permission} restaurant={restaurant} />
        </>
      ) : restaurants?.length && restaurants.length > 0 ? (
        <LoadingCustom />
      ) : (
        <Typography variant="h3">Chưa tạo cửa hàng</Typography>
      )}
    </Stack>
  );
};

export default InfoRestaurant;

const Info = ({
  restaurant,
  permission,
}: {
  restaurant: RestaurantModel;
  permission: boolean;
}) => {
  const { setPageLeft, setPageRight } = useContext(PageContext);
  return (
    <Stack sx={{ paddingTop: 3 }}>
      <Typography
        variant="h5"
        sx={{ fontWeight: "bold", textAlign: "center", width: "100%" }}
      >
        Thông tin nhà hàng
      </Typography>
      <Stack direction={"row"}>
        <Box
          component={"img"}
          src="/images/restaurant.png"
          sx={{ height: 100, borderRadius: 4 }}
        />
        <Stack direction={"column"} width={"50%"} justifyContent={"end"}>
          <Typography variant="h5" sx={{ fontWeight: "bold" }}>
            {restaurant.name}
          </Typography>
          <Typography variant="h5">Địa chỉ: {restaurant.address}</Typography>
          <Typography variant="h5">
            Số điện thoại: {restaurant.phone}
          </Typography>
        </Stack>
        <Stack justifyContent={"space-between"} alignItems={"end"}>
          {permission ? (
            <SettingsIcon
              sx={iconButtonSTL}
              onClick={() => setPageRight("FormEditRestaurant")}
            />
          ) : (
            <ReplyAllIcon
              sx={iconButtonSTL}
              onClick={() => setPageLeft("HomePage")}
            />
          )}
          <Typography variant="h5">
            Trạng thái hiện tại: {restaurant.status}
          </Typography>
        </Stack>
      </Stack>
    </Stack>
  );
};

const Menu = ({
  permission,
  restaurant,
}: {
  permission: boolean;
  restaurant: RestaurantModel;
}) => {
  const { setPageRight, setData } = useContext(PageContext);
  return (
    <>
      <Stack
        direction={"row"}
        alignItems={"center"}
        padding={"10px 20px 0 20px"}
        justifyContent={"space-between"}
      >
        <Stack direction={"row"} alignItems={"center"}>
          <Typography variant="h5" sx={{ fontWeight: "bold" }}>
            Thực đơn nhà hàng
          </Typography>
          {permission && (
            <IconButton
              onClick={() => {
                setPageRight("FormAddMenu");
                setData((data: Record<string, any>) => ({
                  ...data,
                  item: { restaurantID: restaurant.restaurantID },
                }));
              }}
            >
              <BorderColorIcon sx={{ color: "black", fontSize: 30 }} />
            </IconButton>
          )}
        </Stack>

        {permission && (
          <Stack direction={"row"} alignItems={"center"}>
            <Typography variant="h5" sx={{ fontWeight: "bold" }}>
              Đơn hàng
            </Typography>
            <IconButton onClick={() => setPageRight("RestaurantOrders")}>
              <ListAltIcon sx={{ color: "black", fontSize: 30 }} />
            </IconButton>
          </Stack>
        )}
      </Stack>

      <Grid2 container spacing={3}>
        {restaurant.menu.map((item) => (
          <CardItem
            key={item.itemID}
            restaurantName={restaurant.name}
            restaurantID={restaurant.restaurantID}
            item={item}
            permission={permission}
          />
        ))}
      </Grid2>
    </>
  );
};

const iconButtonSTL: React.CSSProperties = {
  fontSize: 40,
  color: "black",
  cursor: "pointer",
};
