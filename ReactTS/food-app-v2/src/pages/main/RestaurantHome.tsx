import { Box } from "@mui/material";
import InfoRestaurant from "../left/InfoRestaurant";
import { useContext } from "react";
import RestaurantOrders from "../right/Restaurant/RestaurantOrders";
import OrdersRestaurant from "../left/OrdersRestaurant";
import FormEditMenu from "../right/Restaurant/FormEditMenu";
import FormEditRestaurant from "../right/Restaurant/FormEditRestaurant";
import { ChangePage, PageContext } from "../../hooks/ChangePage";
import { useRestaurantAPI } from "../../hooks/useAPI/useRestaurantAPI";
import LoadingCustom from "../../components/atoms/LoadingCustom";

const RestaurantHome = () => {
  const { restaurantByUser, isLoadingRestaurantUser } = useRestaurantAPI();

  return isLoadingRestaurantUser ? (
    <LoadingCustom />
  ) : (
    <ChangePage
      defaultPageLeft="InfoRestaurant"
      defaultPageRight="RestaurantOrders"
      defaultData={
        restaurantByUser && restaurantByUser.length > 0
          ? { restaurantID: restaurantByUser[0].restaurantID }
          : undefined
      }
    >
      <LeftBody />
      <RightBody />
    </ChangePage>
  );
};

export default RestaurantHome;

const LeftBody = () => {
  const { pageLeft } = useContext(PageContext);

  return (
    <Box sx={{ width: "75%", minHeight: "90vh" }}>
      {pageLeft === "InfoRestaurant" && <InfoRestaurant permission={true} />}

      {pageLeft === "OrdersRestaurant" && (
        <OrdersRestaurant/>
      )}
    </Box>
  );
};
const RightBody = () => {
  const { pageRight } = useContext(PageContext);

  return (
    <Box sx={{ width: "25%", minHeight: "90vh" }}>
      {pageRight === "FormEditRestaurant" && (
        <FormEditRestaurant styleEdit={true} />
      )}
      {pageRight === "FormAddRestaurant" && <FormEditRestaurant />}
      {pageRight === "FormAddMenu" && <FormEditMenu />}
      {pageRight === "FormEditMenu" && <FormEditMenu styleEdit={true} />}
      {pageRight === "RestaurantOrders" && <RestaurantOrders/>}
    </Box>
  );
};
