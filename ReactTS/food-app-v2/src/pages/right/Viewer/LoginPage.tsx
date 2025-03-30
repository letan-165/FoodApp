import { Avatar, Box, Stack, Typography } from "@mui/material";
import { useContext, useState } from "react";
import { LoginRequest } from "../../../APIs/dto/requests/AuthRequest";
import AuthService from "../../../APIs/services/AuthService";
import ButtonCustom from "../../../components/atoms/ButtonCustom";
import TextFieldCustom from "../../../components/atoms/TextFieldCustom";
import { PageContext } from "../../../hooks/ChangePage";

const LoginPage = () => {
  const [login, setLogin] = useState<LoginRequest>({
    username: "",
    password: "",
  });
  const { setPageRight } = useContext(PageContext);

  return (
    <Stack alignItems="center" width="100%">
      <Typography variant="h5" sx={{ paddingTop: 2, fontWeight: "bold" }}>
        Đăng nhập
      </Typography>

      <TextFieldCustom
        text="Tài khoản"
        onChange={(v) => setLogin({ ...login, username: v })}
      />
      <TextFieldCustom
        text="Mật khẩu"
        type="password"
        onChange={(v) => setLogin({ ...login, password: v })}
      />

      <Box
        style={{
          width: "100%",
          textAlign: "right",
          padding: "5px 0 20px 30px",
        }}
      >
        <Typography
          style={{
            textDecoration: "underline",
            cursor: "pointer",
            color: "red",
            fontStyle: "italic",
          }}
          onClick={() => setPageRight("ForgotPasswordPage")}
        >
          Quên mật khẩu
        </Typography>
      </Box>

      <ButtonCustom
        text="Đăng nhập"
        backgroundColor="#3f41c0"
        fontSize={20}
        borderRadius={4}
        onClick={async () => {
          if (await AuthService.login(login)) {
            window.location.reload();
          }
        }}
      />

      <Stack
        direction={"row"}
        spacing={2}
        alignItems={"center"}
        padding={"10px 0 20px 0"}
      >
        <Typography variant="h5" sx={{ color: "grey" }}>
          Or
        </Typography>
        <Avatar src="/images/google.png" />
      </Stack>

      <Typography variant="h6" sx={{ color: "grey" }}>
        Nhấn vào
        <span
          style={{
            color: "blue",
            fontWeight: "bold",
            textDecoration: "underline",
            fontStyle: "italic",
            cursor: "pointer",
            padding: "0 10px",
          }}
          onClick={() => setPageRight("SignUpPage")}
        >
          đây
        </span>
        để tạo tài khoản.
      </Typography>
    </Stack>
  );
};

export default LoginPage;
