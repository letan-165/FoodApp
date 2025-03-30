import { TextField, IconButton, InputAdornment } from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useState } from "react";

const TextFieldCustom = ({
  text,
  type = "text",
  fontSizeTitle = 12,
  value,
  click = false,
  onChange,
  readOnly = false,
}: {
  type?: string;
  click?: boolean;
  readOnly?: boolean;
  value?: string;
  onChange?: (v: string) => void;
  text: string;
  fontSizeTitle?: number;
}) => {
  const [isHidden, setIsHidden] = useState(type === "password");

  return (
    <div style={{ width: "100%" }}>
      <h4 style={{ marginLeft: 4, fontSize: fontSizeTitle }}>*{text}</h4>
      <TextField
        error={click && !value}
        helperText={click && !value ? `Vui lòng nhập *${text}` : null}
        value={value}
        onChange={(v) => onChange?.(v.target.value)}
        type={isHidden ? "password" : "text"}
        variant="outlined"
        placeholder={`Nhập ${text}...`}
        sx={{
          width: "100%",
          paddingBottom: 1,
          "& fieldset": {
            borderRadius: 4,
            border: "solid 1px ",
          },
        }}
        slotProps={{
          input: {
            readOnly: readOnly,
            endAdornment:
              type === "password" ? (
                <InputAdornment position="end">
                  <IconButton onClick={() => setIsHidden(!isHidden)} edge="end">
                    {isHidden ? <VisibilityOff /> : <Visibility />}
                  </IconButton>
                </InputAdornment>
              ) : null,
          },
        }}
      />
    </div>
  );
};

export default TextFieldCustom;
