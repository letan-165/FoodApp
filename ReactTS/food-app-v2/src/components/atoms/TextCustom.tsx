import { Typography } from "@mui/material";

const TextCustom = ({ title, text }: { title: string; text: string }) => {
  return (
    <Typography variant="h6" sx={{ fontWeight: "bold" }}>
      {title} <span style={{ fontWeight: "normal" }}>{text}</span>
    </Typography>
  );
};

export default TextCustom;
