import { Box, Button } from "@mui/material";
import { ReactNode } from "react";
import StoreIcon from "@mui/icons-material/Store";
import MopedIcon from "@mui/icons-material/Moped";
import { useNavigate } from "react-router-dom";
import AuthService from "../../../APIs/services/AuthService";
const CardOption = ({
  name,
  icon,
  onClick,
}: {
  name: string;
  icon: ReactNode;
  onClick?: () => void;
}) => {
  return (
    <Button
      onClick={onClick}
      startIcon={icon}
      sx={{
        display: "flex",
        color: "black",
        borderBottom: "solid 1px",
        fontSize: 15,
        textTransform: "none",
      }}
    >
      {name}
    </Button>
  );
};

const MenuAnchor = () => {
  const navigator = useNavigate();
  return (
    <Box
      sx={{
        width: 150,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        borderRadius: 5,
        gap: 1,
        padding: 2,
      }}
    >
      <CardOption
        name="Cửa hàng"
        icon={<StoreIcon />}
        onClick={() => navigator("/restaurant")}
      />
      <CardOption
        name="Giao hàng"
        icon={<MopedIcon />}
        onClick={() => navigator("/shipper")}
      />
      <Button
        onClick={async () => {
          if (await AuthService.logout()) {
            localStorage.removeItem("userName");
            window.location.reload();
          }
        }}
        sx={{
          fontSize: 15,
          color: "red",
          textDecoration: "underline",
          fontWeight: "bold",
        }}
      >
        Đăng xuất
      </Button>
    </Box>
  );
};

export default MenuAnchor;
