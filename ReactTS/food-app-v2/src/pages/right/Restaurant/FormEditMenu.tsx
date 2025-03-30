import { IconButton, Stack, Typography } from "@mui/material";
import ButtonCustom from "../../../components/atoms/ButtonCustom";
import TextFieldCustom from "../../../components/atoms/TextFieldCustom";
import { useContext, useEffect, useState } from "react";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { ItemResponse } from "../../../APIs/dto/responses/ItemResponse";
import { ItemRequest } from "../../../APIs/dto/requests/RestaurantRequest";
import { PageContext } from "../../../hooks/ChangePage";
import { useRestaurantAPI } from "../../../hooks/useAPI/useRestaurantAPI";

const FormEditMenu = ({ styleEdit = false }: { styleEdit?: boolean }) => {
  const { data } = useContext(PageContext);
  const [item, setItem] = useState<ItemResponse | undefined>(undefined);
  const { restaurantByUser, addItem, deleteItem, updateItem } =
    useRestaurantAPI();
  const [restaurantID, setRestaurantID] = useState<string>("");
  const [request, setRequest] = useState<ItemRequest>({
    name: "",
    price: 0,
    quantity: 0,
  });

  useEffect(() => {
    const { restaurantID, itemID } = data.item;
    setRestaurantID(restaurantID);
    if (!styleEdit) return;

    setItem(
      restaurantByUser
        ?.find(({ restaurantID: id }) => id === restaurantID)
        ?.menu.find(({ itemID: id }) => id === itemID)
    );
    item &&
      setRequest({
        name: item.name,
        price: item.price,
        quantity: item.quantity,
      });
  }, [data.item, item]);
  const [click, setClick] = useState<boolean>(false);

  return (
    <Stack alignItems={"center"} gap={1}>
      <Typography variant="h5" sx={{ fontWeight: "bold" }}>
        {styleEdit ? "Sửa món ăn" : "Thêm món ăn"}
      </Typography>

      {styleEdit && item && (
        <TextFieldCustom
          readOnly={true}
          text="Mã sản phẩm"
          fontSizeTitle={17}
          value={`${item.itemID}`}
          type="number"
        />
      )}
      <TextFieldCustom
        text="Tên sản phẩm"
        fontSizeTitle={17}
        value={request.name}
        onChange={(v) => setRequest({ ...request, name: v })}
        click={click}
      />
      <TextFieldCustom
        text="Giá sản phẩm"
        fontSizeTitle={17}
        value={`${request.price}`}
        onChange={(v) => setRequest({ ...request, price: Number(v) })}
        click={click}
      />
      <TextFieldCustom
        text="Số lượng sản phẩm"
        fontSizeTitle={17}
        value={`${request.quantity}`}
        onChange={(v) => setRequest({ ...request, quantity: Number(v) })}
        click={click}
      />

      <Stack direction={"row"} spacing={2} alignItems={"center"}>
        <ButtonCustom
          text={item ? "Cập nhật" : "Thêm"}
          fontSize={20}
          borderRadius={3}
          onClick={async () => {
            setClick(true);
            if (!request.name || !request.price || !request.quantity) {
              return;
            }
            if (styleEdit && item) {
              updateItem.mutate(
                { restaurantID, itemID: item.itemID, request },
                {
                  onSuccess: () => alert(`Cập nhật${request.name} thành công`),
                  onError: (e) => console.error(e),
                }
              );
            } else {
              addItem.mutate(
                { restaurantID, request },
                {
                  onSuccess: () => alert(`Thêm ${request.name} thành công`),
                  onError: (e) => console.error(e),
                }
              );
            }
          }}
        />
        {styleEdit && item && (
          <IconButton
            sx={{ border: "solid 1px black", width: 50, height: 50 }}
            onClick={async () =>
              deleteItem.mutate(
                { restaurantID, itemID: item.itemID },
                {
                  onSuccess: () => alert(`Xóa ${request.name} thành công`),
                  onError: (e) => console.error(e),
                }
              )
            }
          >
            <DeleteForeverIcon sx={{ fontSize: 30, color: "red" }} />
          </IconButton>
        )}
      </Stack>
    </Stack>
  );
};

export default FormEditMenu;
