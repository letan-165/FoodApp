import { Avatar, Stack, Typography } from "@mui/material";
import React, { useContext } from "react";
import RestaurantModel from "../../../models/RestaurantModel";
import IconStyle from "../../../styles/IconStyle";
import { PageContext } from "../../../hooks/ChangePage";

function CardRestaurant({ restaurant }: { restaurant: RestaurantModel }) {
  const { setData, setPageLeft } = useContext(PageContext);
  return (
    <Stack
      sx={cardBox}
      onClick={() => {
        setData((data: Record<string, any>) => ({
          ...data,
          restaurantID: restaurant.restaurantID,
        })),
          setPageLeft("InfoRestaurant");
      }}
    >
      <Avatar sx={IconStyle(30)} />
      <Typography sx={nameStyle}>{restaurant.name}</Typography>
    </Stack>
  );
}
export default CardRestaurant;

export const cardBox: React.CSSProperties = {
  width: 90,
  height: 90,
  justifyContent: "center",
  alignItems: "center",
  textAlign: "center",
  borderRadius: 5,
  boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.2)",
};

const nameStyle: React.CSSProperties = {
  fontSize: 13,
  lineHeight: 1,
  overflow: "hidden",
  textOverflow: "ellipsis",
  maxWidth: "100%",
  padding: "10px 5px",
  whiteSpace: "nowrap",
  cursor: "pointer",
};
