import { Button } from "@mui/material";

const ButtonCustom = ({
  text,
  color = "white",
  width,
  borderRadius = 1,
  fontSize = 14,
  backgroundColor = "#3fc0b8",
  onClick,
}: {
  color?: string;
  width?: string;
  text: string;
  borderRadius?: number;
  fontSize?: number;
  backgroundColor?: string;
  onClick?: () => void;
}) => {
  return (
    <Button
      variant="contained"
      sx={{
        color: color,
        width: width,
        fontSize: fontSize,
        padding: "10px 30px",
        backgroundColor: backgroundColor,
        borderRadius: borderRadius,
        textTransform: "none",
      }}
      onClick={onClick}
    >
      {text}
    </Button>
  );
};

export default ButtonCustom;
