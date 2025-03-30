import { Grid2, Stack, Typography } from "@mui/material";
import ListCardRestaurant from "../../components/organisms/ListCartRestaurant";
import RestaurantModel from "../../models/RestaurantModel";
import CardItem from "../../components/molecules/Card/CardItem";
import { useRestaurantAPI } from "../../hooks/useAPI/useRestaurantAPI";

function HomePage() {
  const { restaurants } = useRestaurantAPI();
  return (
    <Stack minHeight={"90vh"}>
      <ListCardRestaurant />
      <Typography variant="h5" sx={{ paddingTop: 2, fontWeight: "bold" }}>
        Món ăn nổi bậc
      </Typography>
      {restaurants && (
        <Grid2 container spacing={3}>
          {restaurants.map((restaurant: RestaurantModel) =>
            restaurant.menu.map((item) => (
              <CardItem
                key={item.itemID}
                item={item}
                restaurantName={restaurant.name}
                restaurantID={restaurant.restaurantID}
              />
            ))
          )}
        </Grid2>
      )}
    </Stack>
  );
}
export default HomePage;
