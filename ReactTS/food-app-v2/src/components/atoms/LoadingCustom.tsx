import { CircularProgress } from "@mui/material";

const LoadingCustom = ({ size = 40 }: { size?: number }) => {
  return <CircularProgress size={size} />;
};
export default LoadingCustom;
