import { Box, IconButton, Stack, Typography } from "@mui/material";
import UndoIcon from "@mui/icons-material/Undo";
import { useContext, useState } from "react";
import { UserSaveRequest } from "../../../APIs/dto/requests/UserRequest";
import UserService from "../../../APIs/services/UserService";
import ButtonCustom from "../../../components/atoms/ButtonCustom";
import TextFieldCustom from "../../../components/atoms/TextFieldCustom";
import { PageContext } from "../../../hooks/ChangePage";

const SignUpPage = () => {
  const [user, setUser] = useState<UserSaveRequest>({
    name: "",
    password: "",
    phone: "",
    gmail: "",
  });

  const [click, setClick] = useState(false);
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
        Đăng kí
      </Typography>

      <TextFieldCustom
        text="Tài khoản"
        value={user.name}
        onChange={(v) => setUser({ ...user, name: v })}
        click={click}
      />
      <TextFieldCustom
        text="Mật khẩu"
        type="password"
        value={user.password}
        onChange={(v) => setUser({ ...user, password: v })}
        click={click}
      />
      <TextFieldCustom
        text="Số điện thoại"
        value={user.phone}
        onChange={(v) => setUser({ ...user, phone: v })}
        click={click}
      />
      <TextFieldCustom
        text="Gmail"
        value={user.gmail}
        onChange={(v) => setUser({ ...user, gmail: v })}
        click={click}
      />
      <ButtonCustom
        text="Đăng Kí"
        backgroundColor="#3f41c0"
        fontSize={20}
        borderRadius={4}
        onClick={async () => {
          setClick(true);
          (await UserService.save(user)) ? setPageRight("LoginPage") : null;
        }}
      />
    </Stack>
  );
};

export default SignUpPage;
