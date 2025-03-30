import { createContext, useReducer, ReactNode, Dispatch } from "react";
import { ItemResponse } from "../APIs/dto/responses/ItemResponse";

const initState: Record<string, ItemResponse[]> = {};

type Action = { checked: boolean; restaurantID: string; item: ItemResponse };

type CartContextType = {
  state: Record<string, ItemResponse[]>;
  dispatch: Dispatch<Action>;
};

export const CartContext = createContext<CartContextType>({
  state: initState,
  dispatch: () => {},
});

function reducer(
  state: Record<string, ItemResponse[]>,
  action: Action
): Record<string, ItemResponse[]> {
  const { checked, restaurantID, item } = action;
  let updatedState = { ...state };
  // Xoá sản phẩm để thêm lại
  updatedState[restaurantID] =
    updatedState[restaurantID]?.filter((i) => i.itemID != item.itemID) || [];

  //Xóa cửa hàng rỗng
  if (updatedState[restaurantID].length === 0) {
    delete updatedState[restaurantID];
  }

  if (checked) {
    updatedState[restaurantID] = [...(updatedState[restaurantID] || []), item];
  }

  return updatedState;
}

// CartProvider để bọc toàn bộ ứng dụng
export function TotalCart({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(reducer, initState);

  return (
    <CartContext.Provider value={{ state, dispatch }}>
      {children}
    </CartContext.Provider>
  );
}
