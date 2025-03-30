import {
  Stack,
  Typography,
  Box,
  FormControl,
  FormLabel,
  RadioGroup,
  FormControlLabel,
  Radio,
} from "@mui/material";
import TextCustom from "../../components/atoms/TextCustom";
import TextFieldCustom from "../../components/atoms/TextFieldCustom";
import UserService from "../../APIs/services/UserService";
import ButtonCustom from "../../components/atoms/ButtonCustom";
import { UserResponse } from "../../APIs/dto/responses/UserResponse";
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { CartContext } from "../../hooks/TotalCart";
import {
  ItemSaveOrderRequest,
  OrderSaveFromCartRequest,
} from "../../APIs/dto/requests/OrderRequest";
import { useOrderAPI } from "../../hooks/useAPI/useOrderAPI";

const InfoPayment = () => {
  const navigator = useNavigate();
  const { state } = useContext(CartContext);
  const { addOrder } = useOrderAPI();
  const [user, setUser] = useState<UserResponse | null>(null);
  const [phone, setPhone] = useState<string>("");
  const [address, setAddress] = useState<string>("");

  useEffect(() => {
    const userID = localStorage.getItem("userID") || "";
    const getUser = async () => {
      try {
        setUser(await UserService.findById(userID));
      } catch (error) {
        console.error(error);
      }
    };
    getUser();
  }, []);
  useEffect(() => {
    user && setPhone(user.phone);
  }, [user]);

  return (
    <Stack width={"100%"} height={"90vh"} spacing={2}>
      <Typography variant="h4" sx={{ fontWeight: "bold", textAlign: "center" }}>
        Thông tin đơn hàng
      </Typography>

      <Stack direction={"row"}>
        <Stack width={"50%"}>
          <TextCustom title="MãKH:" text={user?.userID || ""} />
          <TextCustom title="TênKH::" text={user?.name || ""} />

          <Box width={"80%"}>
            <TextFieldCustom
              text="Địa chỉ:"
              fontSizeTitle={20}
              value={address}
              onChange={setAddress}
            />
            <TextFieldCustom
              text="Số điện thoại:"
              fontSizeTitle={20}
              value={phone}
              onChange={setPhone}
            />
          </Box>
        </Stack>

        <Stack width={"50%"}>
          <FormControl>
            <FormLabel
              sx={{ color: "black", fontSize: 20, fontWeight: "bold" }}
            >
              Phương thức thanh toán:
            </FormLabel>
            <RadioGroup defaultValue="cash">
              <FormControlLabel
                value="cash"
                control={<Radio />}
                label="Tiền mặt"
              />
              <FormControlLabel
                value="bank"
                control={<Radio />}
                label="Chuyển khoản"
              />
            </RadioGroup>
          </FormControl>
        </Stack>
      </Stack>

      <ButtonCustom
        text="Đặt hàng"
        width={"20%"}
        fontSize={20}
        borderRadius={3}
        backgroundColor="#3f41c0"
        onClick={async () => {
          if (user) {
            await Promise.all(
              Object.entries(state).map(
                async ([restaurantID, itemsResponse]) => {
                  const menu: ItemSaveOrderRequest[] = itemsResponse.map(
                    (item) => ({
                      itemID: item.itemID,
                      quantity: item.quantity,
                    })
                  );

                  const request: OrderSaveFromCartRequest = {
                    customerID: user.userID,
                    restaurantID,
                    menu,
                    address: address,
                    phone: phone,
                  };

                  addOrder.mutate(request, {
                    onSuccess: () =>
                      console.log("Thêm ", request.restaurantID, " thành công"),
                    onError: (e) => console.error(e),
                  });
                }
              )
            );

            navigator("/customerFinal");
          }
        }}
      />
    </Stack>
  );
};

export default InfoPayment;
