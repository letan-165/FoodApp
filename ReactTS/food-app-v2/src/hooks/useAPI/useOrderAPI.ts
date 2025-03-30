import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import OrderService from "../../APIs/services/OrderService";
import {
  OrderSaveFromCartRequest,
  UpdateStatusRequest,
} from "../../APIs/dto/requests/OrderRequest";

export const useOrderAPI = ({
  restaurantID = "",
  orderID = "",
}: {
  restaurantID?: string;
  orderID?: string;
} = {}) => {
  const handleReload = () => {
    queryClient.invalidateQueries({ queryKey: ["ordersUser"] });
    queryClient.invalidateQueries({ queryKey: ["ordersRestaurant"] });
    queryClient.invalidateQueries({ queryKey: ["orderShipper"] });
    queryClient.invalidateQueries({ queryKey: ["ordersConfirmed"] });
    queryClient.invalidateQueries({ queryKey: ["getOrder"] });
  };

  const queryClient = useQueryClient();
  const { data: ordersUser } = useQuery({
    queryKey: ["ordersUser"],
    queryFn: OrderService.getAllByUser,
  });
  const { data: ordersRestaurant, refetch: refetchRestaurant } = useQuery({
    queryKey: ["ordersRestaurant"],
    queryFn: () => OrderService.getAllByRestaurant(restaurantID || ""),
    enabled: !!restaurantID,
  });
  const { data: orderShipper } = useQuery({
    queryKey: ["orderShipper"],
    queryFn: OrderService.getAllByShipper,
  });

  const { data: ordersConfirmed } = useQuery({
    queryKey: ["ordersConfirmed"],
    queryFn: () => OrderService.getAllStatus("CONFIRMED"),
  });

  const { data: getOrder, refetch: refetchOrder } = useQuery({
    queryKey: ["getOrder"],
    queryFn: () => OrderService.getOrder(orderID || ""),
    enabled: !!orderID,
  });

  const addOrder = useMutation({
    mutationFn: async (request: OrderSaveFromCartRequest) =>
      OrderService.saveFromCart({ request }),
    onSuccess: handleReload,
  });
  const updateStatus = useMutation({
    mutationFn: async ({
      orderID,
      request,
    }: {
      orderID: string;
      request: UpdateStatusRequest;
    }) => OrderService.updateStatus({ orderID, request }),
    onSuccess: handleReload,
  });
  return {
    getOrder,
    ordersUser,
    ordersConfirmed,
    updateStatus,
    ordersRestaurant,
    orderShipper,
    addOrder,
    refetchRestaurant,
    refetchOrder,
  };
};
