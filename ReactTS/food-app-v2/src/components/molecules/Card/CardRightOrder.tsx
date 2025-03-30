import { Stack, Typography } from "@mui/material";
import AssignmentIcon from "@mui/icons-material/Assignment";
import SelectCustom from "../../atoms/SelectCustom";
import { useState } from "react";
import { OrderResponse } from "../../../APIs/dto/responses/OrderResponse";
import { useOrderAPI } from "../../../hooks/useAPI/useOrderAPI";

const CardRightOrder = ({
  order,
  typeRestaurant = false,
  onClick,
}: {
  typeRestaurant?: boolean;
  order: OrderResponse;
  onClick: () => void;
}) => {
  const [status, setStatus] = useState<string>(order.status);
  const { updateStatus } = useOrderAPI();
  return (
    <Stack
      direction={"row"}
      width={"100%"}
      border={"solid 1px"}
      borderRadius={3}
      padding={1}
      sx={{ cursor: "pointer" }}
    >
      <Stack direction={"row"} width={"70%"} onClick={onClick}>
        <AssignmentIcon sx={{ fontSize: 60 }} />
        <Stack flexGrow={1} justifyContent={"center"}>
          <Typography sx={{ fontSize: 12, color: "black", fontWeight: "bold" }}>
            ID: {order.orderID}
          </Typography>
          <Typography
            sx={{ fontSize: 12, color: "black" }}
          >{`${order.time}`}</Typography>
        </Stack>
      </Stack>
      {typeRestaurant && (
        <SelectCustom
          height={50}
          values={["Chưa", "Xong"]}
          defaultValue={status === "PENDING" ? "Chưa" : "Xong"}
          onChange={(s) => {
            const s1: string = s === "Xong" ? "CONFIRMED" : "PENDING";

            updateStatus.mutate(
              {
                orderID: order.orderID,
                request: { shipperID: null, status: s1 },
              },
              {
                onSuccess: () => setStatus(s1),
                onError: (e) => console.error(e),
              }
            );
          }}
          width={100}
        />
      )}
    </Stack>
  );
};

export default CardRightOrder;
