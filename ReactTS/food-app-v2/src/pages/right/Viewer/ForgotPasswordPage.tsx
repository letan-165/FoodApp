import { Box, IconButton, Stack, Typography } from "@mui/material";
import UndoIcon from "@mui/icons-material/Undo";
import ButtonCustom from "../../../components/atoms/ButtonCustom";
import TextFieldCustom from "../../../components/atoms/TextFieldCustom";
import { useContext } from "react";
import { PageContext } from "../../../hooks/ChangePage";

const ForgotPasswordPage = () => {
  const { setPageRight } = useContext(PageContext);
  return (
    <Stack direction={"column"} alignItems="center" width="100%">
      <Box width="100%">
        <IconButton
          sx={{ border: "solid 1px black" }}
          onClick={() => setPageRight("LoginPage")}
        >
          <UndoIcon sx={{ color: "black", fontSize: 30, textAlign: "start" }} />
        </IconButton>
      </Box>
      <Typography variant="h5" sx={{ paddingTop: 2, fontWeight: "bold" }}>
        Lấy lại mật khẩu
      </Typography>

      <TextFieldCustom text="Tài khoản" />
      <TextFieldCustom text="Gmail" />
      <TextFieldCustom text="Mã xác nhận" />
      <ButtonCustom
        text="Xác nhận"
        backgroundColor="#3f41c0"
        fontSize={20}
        borderRadius={4}
        onClick={() => setPageRight("LoginPage")}
      />
    </Stack>
  );
};

export default ForgotPasswordPage;
