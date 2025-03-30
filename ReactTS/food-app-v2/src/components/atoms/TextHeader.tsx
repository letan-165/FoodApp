import { Typography } from "@mui/material";

const TextHeader = ({
  text,
  click,
  onChange,
}: {
  text: string;
  click: boolean;
  onChange: () => void;
}) => {
  return (
    <Typography
      onClick={onChange}
      variant="h5"
      sx={
        click
          ? { fontWeight: "bold", color: "blue" }
          : { color: "grey", cursor: "pointer" }
      }
    >
      {text}
    </Typography>
  );
};

export default TextHeader;
