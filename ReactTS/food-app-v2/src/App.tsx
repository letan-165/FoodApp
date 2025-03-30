import { Box, Stack } from "@mui/material";
import AppBarCustom from "./components/organisms/AppBarCustom";
import ViewerHome from "./pages/main/ViewerHome";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import CustomerHome from "./pages/main/CustomerHome";
import CustomerFinal from "./pages/main/CustomerFinal";
import RestaurantHome from "./pages/main/RestaurantHome";
import ShipperHome from "./pages/main/ShipperHome";
import { useEffect, useState } from "react";
import AuthService from "./APIs/services/AuthService";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

function App() {
  const userID = localStorage.getItem("userName") || "";
  const [auth, setAuth] = useState<boolean>(true);

  useEffect(() => {
    const instropect = async () => {
      try {
        const check = await AuthService.instropect();
        setAuth(check);
      } catch (error) {
        console.error(error);
      }
    };
    instropect();
  }, []);
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Stack sx={boxSTL}>
          <Box sx={containerSTL}>
            <AppBarCustom userID={userID} />
            <Stack direction={"row"} sx={{ height: "100%", marginTop: 2 }}>
              <Routes>
                <Route
                  path="/"
                  element={auth ? <CustomerHome /> : <ViewerHome />}
                />
                <Route path="/customerFinal" element={<CustomerFinal />} />
                <Route path="/restaurant" element={<RestaurantHome />} />
                <Route path="/Shipper" element={<ShipperHome />} />
              </Routes>
            </Stack>
          </Box>
        </Stack>
      </BrowserRouter>
    </QueryClientProvider>
  );
}

export default App;

const containerSTL: React.CSSProperties = {
  backgroundColor: "white",
  width: "100%",
  borderRadius: 3,
  padding: "0 30px",
};

const boxSTL: React.CSSProperties = {
  justifyContent: "center",
  alignItems: "center",
  padding: "20px",
};
