import NotificationsIcon from "@mui/icons-material/Notifications";
import IconStyle from "../../../styles/IconStyle";
import { Stack, IconButton, Typography, Avatar, Popover } from "@mui/material";
import MenuAnchor from "./MenuAnchor";
import { useState } from "react";

const RightBar = ({ userID }: { userID?: string }) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  return (
    <Stack direction={"row"} alignItems={"center"}>
      <IconButton>
        <NotificationsIcon sx={IconStyle(40, 2)} />
      </IconButton>
      {userID && (
        <Typography variant="h5" sx={{ color: "black" }}>
          {`Hi, ${userID}`}
        </Typography>
      )}
      <Avatar
        alt="avatar"
        src={userID ? `/images/avatar2.png` : "/images/avatar.png"}
        sx={{ cursor: "pointer", margin: 1, width: 60, height: 60 }}
        onClick={(e) => setAnchorEl(e.currentTarget)}
      />
      <Popover
        open={Boolean(anchorEl) && userID != ""}
        anchorEl={anchorEl}
        onClose={() => setAnchorEl(null)}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "left",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "center",
        }}
      >
        <MenuAnchor />
      </Popover>
    </Stack>
  );
};

export default RightBar;
