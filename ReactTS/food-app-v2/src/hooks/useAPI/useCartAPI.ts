import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import CartService from "../../APIs/services/CartService";
import { CartAddItemRequest } from "../../APIs/dto/requests/CartRequest";

export const useCartAPI = () => {
  const queryClient = useQueryClient();

  const { data: carts, isLoading } = useQuery({
    queryKey: ["carts"],
    queryFn: CartService.findCartUser,
  });

  const addItem = useMutation({
    mutationFn: (request: CartAddItemRequest) => CartService.addItem(request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["carts"] }),
  });

  return { carts, isLoading, addItem };
};
