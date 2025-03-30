import { Stack, Avatar, Typography, IconButton } from "@mui/material";
import { useNavigate } from "react-router-dom";
import IconStyle from "../../../styles/IconStyle";
import AssignmentIcon from "@mui/icons-material/Assignment";

const LeftBar = ({ userID }: { userID?: string }) => {
  const navigator = useNavigate();
  return (
    <Stack direction={"row"}>
      <Stack
        direction={"row"}
        sx={{ cursor: "pointer" }}
        onClick={() => {
          navigator("/");
          window.location.reload();
        }}
      >
        <Avatar alt="Logo" src="/images/logo.png" sx={IconStyle(60, 2)} />
        <Typography
          variant="h2"
          sx={{
            color: "black",
            width: 10,
            fontSize: 25,
            fontWeight: "bold",
            marginRight: 10,
          }}
        >
          Food App
        </Typography>
      </Stack>

      <IconButton onClick={() => navigator(userID ? "/customerFinal" : "/")}>
        <AssignmentIcon sx={IconStyle(40)} />
      </IconButton>
    </Stack>
  );
};

export default LeftBar;
