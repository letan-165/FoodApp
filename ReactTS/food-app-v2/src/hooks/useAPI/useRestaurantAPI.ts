import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import RestaurantService from "../../APIs/services/RestaurantService";
import {
  RestaurantRequest,
  ItemRequest,
} from "../../APIs/dto/requests/RestaurantRequest";

export const useRestaurantAPI = () => {
  const queryClient = useQueryClient();

  const handleReload = () => {
    queryClient.invalidateQueries({ queryKey: ["restaurantByUser"] });
    queryClient.invalidateQueries({ queryKey: ["restaurants"] });
  };

  // Fetch danh sách nhà hàng
  const { data: restaurants, isLoading: isLoading } = useQuery({
    queryKey: ["restaurants"],
    queryFn: RestaurantService.getAll,
  });

  // Fetch danh sách nhà hàng theo User
  const { data: restaurantByUser, isLoading: isLoadingRestaurantUser } =
    useQuery({
      queryKey: ["restaurantByUser"],
      queryFn: RestaurantService.getAllByUser,
    });

  // Thêm nhà hàng
  const addRestaurant = useMutation({
    mutationFn: (request: RestaurantRequest) =>
      RestaurantService.addRestaurant(request),
    onSuccess: handleReload,
  });

  // Cập nhật nhà hàng
  const updateRestaurant = useMutation({
    mutationFn: ({ id, request }: { id: string; request: RestaurantRequest }) =>
      RestaurantService.updateRestaurant(id, request),
    onSuccess: handleReload,
  });

  // Xóa nhà hàng
  const deleteRestaurant = useMutation({
    mutationFn: (id: string) => RestaurantService.deleteRestaurant(id),
    onSuccess: handleReload,
  });

  // Thêm món ăn vào nhà hàng
  const addItem = useMutation({
    mutationFn: ({
      restaurantID,
      request,
    }: {
      restaurantID: string;
      request: ItemRequest;
    }) => RestaurantService.addItem(restaurantID, request),
    onSuccess: handleReload,
  });

  // Cập nhật món ăn
  const updateItem = useMutation({
    mutationFn: ({
      restaurantID,
      itemID,
      request,
    }: {
      restaurantID: string;
      itemID: number;
      request: ItemRequest;
    }) => RestaurantService.updateItem(restaurantID, itemID, request),
    onSuccess: handleReload,
  });

  // Xóa món ăn
  const deleteItem = useMutation({
    mutationFn: ({
      restaurantID,
      itemID,
    }: {
      restaurantID: string;
      itemID: number;
    }) => RestaurantService.deleteItem(restaurantID, itemID),
    onSuccess: handleReload,
  });

  return {
    restaurants,
    restaurantByUser,
    isLoading,
    isLoadingRestaurantUser,
    addRestaurant,
    updateRestaurant,
    deleteRestaurant,
    addItem,
    updateItem,
    deleteItem,
  };
};
