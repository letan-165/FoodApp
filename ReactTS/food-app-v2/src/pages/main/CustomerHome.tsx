import { Box } from "@mui/material";
import HomePage from "../left/HomePage";
import { useContext } from "react";
import CartPage from "../right/Customer/CartPage";
import InfoRestaurant from "../left/InfoRestaurant";
import { ChangePage, PageContext } from "../../hooks/ChangePage";
import { TotalCart } from "../../hooks/TotalCart";
import PaymentPage from "../right/Customer/PaymentPage";
import InfoPayment from "../left/InfoPayment";

const LeftBody = () => {
  const { pageLeft } = useContext(PageContext);

  return (
    <Box sx={{ width: "73%", borderRight: "solid 1px" }}>
      {pageLeft === "HomePage" && <HomePage />}
      {pageLeft === "InfoRestaurant" && <InfoRestaurant />}
      {pageLeft === "InfoPayment" && <InfoPayment />}
    </Box>
  );
};

const RightBody = () => {
  const { pageRight } = useContext(PageContext);
  return (
    <Box sx={{ width: "27%", borderRight: "solid 1px", paddingLeft: 2 }}>
      {pageRight === "CartPage" && <CartPage />}
      {pageRight === "PaymentPage" && <PaymentPage />}
    </Box>
  );
};

const CustomerHome = () => {
  return (
    <ChangePage defaultPageLeft="HomePage" defaultPageRight="CartPage">
      <TotalCart>
        <LeftBody />
        <RightBody />
      </TotalCart>
    </ChangePage>
  );
};

export default CustomerHome;
