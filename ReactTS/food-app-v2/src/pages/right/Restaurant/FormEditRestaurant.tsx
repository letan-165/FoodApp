import { IconButton, Stack, Typography } from "@mui/material";
import ButtonCustom from "../../../components/atoms/ButtonCustom";
import SelectCustom from "../../../components/atoms/SelectCustom";
import TextFieldCustom from "../../../components/atoms/TextFieldCustom";
import { useContext, useEffect, useState } from "react";
import { RestaurantRequest } from "../../../APIs/dto/requests/RestaurantRequest";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { PageContext } from "../../../hooks/ChangePage";
import { useRestaurantAPI } from "../../../hooks/useAPI/useRestaurantAPI";
import RestaurantModel from "../../../models/RestaurantModel";

const FormEditRestaurant = ({ styleEdit = false }: { styleEdit?: boolean }) => {
  const states = ["PENDING", "CLOSED", "BUSY", "OPEN"];
  const { data } = useContext(PageContext);
  const { restaurantByUser } = useRestaurantAPI();

  const [restaurant, setRestaurant] = useState<RestaurantModel | undefined>(
    undefined
  );

  const [request, setRequest] = useState<RestaurantRequest>({
    userID: localStorage.getItem("userID") || "",
    name: "",
    address: "",
    phone: "",
    status: "",
  });

  useEffect(() => {
    setRestaurant(
      restaurantByUser?.find(
        (restaurant) => restaurant.restaurantID === data.restaurantID
      )
    );
    restaurant &&
      styleEdit &&
      setRequest((data) => ({
        ...data,
        name: restaurant.name,
        address: restaurant.address,
        phone: restaurant.phone,
        status: restaurant.status,
      }));
  }, [restaurant, data.restaurantID]);

  const [click, setClick] = useState<boolean>(false);

  const { addRestaurant, updateRestaurant, deleteRestaurant } =
    useRestaurantAPI();

  return (
    <Stack alignItems={"center"} gap={1}>
      <Typography variant="h5" sx={{ fontWeight: "bold" }}>
        {restaurant ? "Sửa thông tin" : "Thêm nhà hàng"}
      </Typography>

      <TextFieldCustom
        text="Tên cửa hàng"
        fontSizeTitle={17}
        value={request.name}
        onChange={(v) => setRequest({ ...request, name: v })}
        click={click}
      />
      <TextFieldCustom
        text="Địa chỉ"
        fontSizeTitle={17}
        value={request.address}
        onChange={(v) => setRequest({ ...request, address: v })}
        click={click}
      />
      <TextFieldCustom
        text="Số điện thoại"
        fontSizeTitle={17}
        value={request.phone}
        onChange={(v) => setRequest({ ...request, phone: v })}
        click={click}
      />
      {styleEdit && (
        <Stack direction={"row"} width={"100%"} alignItems={"center"} gap={2}>
          <Typography variant="h6" sx={{ fontWeight: "bold" }}>
            Trạng thái
          </Typography>
          <SelectCustom
            values={states}
            defaultValue={request.status}
            width={"30%"}
            onChange={(v) => setRequest({ ...request, status: v })}
          />
        </Stack>
      )}
      <Stack direction={"row"} spacing={2} alignItems={"center"}>
        <ButtonCustom
          text={styleEdit ? "Cập nhật" : "Thêm"}
          fontSize={20}
          borderRadius={3}
          onClick={async () => {
            setClick(true);
            if (!request.name || !request.address || !request.phone) {
              return;
            }

            if (styleEdit && restaurant) {
              updateRestaurant.mutate(
                { id: restaurant.restaurantID, request },
                {
                  onSuccess: () => alert("Cập nhật thành công"),
                  onError: (error) => {
                    console.error("Lỗi khi cập nhật nhà hàng:", error);
                  },
                }
              );
            } else {
              addRestaurant.mutate(request, {
                onSuccess: () => alert("Thêm thành công"),
                onError: (error) => {
                  console.error("Lỗi khi thêm nhà hàng:", error);
                },
              });
            }
          }}
        />
        {styleEdit && restaurant && (
          <IconButton
            sx={{ border: "solid 1px black", width: 50, height: 50 }}
            onClick={async () => {
              deleteRestaurant.mutate(restaurant.restaurantID, {
                onSuccess: () => alert("Xóa cửa hàng thành công"),
                onError: (e) => console.error(e),
              });
            }}
          >
            <DeleteForeverIcon sx={{ fontSize: 30, color: "red" }} />
          </IconButton>
        )}
      </Stack>
    </Stack>
  );
};

export default FormEditRestaurant;
