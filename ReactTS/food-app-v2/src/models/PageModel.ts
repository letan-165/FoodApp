export interface PageModel {
  pageLeft: string;
  setPageLeft: (page: string) => void;
  pageRight: string;
  setPageRight: (page: string) => void;
  data: Record<string, any>;
  setData: (data: Record<string, any>) => void;
}
