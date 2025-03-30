import { createContext, useState, ReactNode } from "react";
import { PageModel } from "../models/PageModel";

export const PageContext = createContext<PageModel>({
  pageLeft: "",
  setPageLeft: () => {},
  pageRight: "",
  setPageRight: () => {},
  data: {},
  setData: () => {},
});

export const ChangePage = ({
  defaultPageLeft = "",
  defaultPageRight = "",
  children,
  defaultData = {},
}: {
  defaultData?: Record<string, any>;
  defaultPageLeft?: string;
  defaultPageRight?: string;
  children: ReactNode;
}) => {
  const [pageLeft, setPageLeft] = useState(defaultPageLeft);
  const [pageRight, setPageRight] = useState(defaultPageRight);
  const [data, setData] = useState<Record<string, any>>(defaultData);

  return (
    <PageContext.Provider
      value={{ pageLeft, setPageLeft, pageRight, setPageRight, data, setData }}
    >
      {children}
    </PageContext.Provider>
  );
};
