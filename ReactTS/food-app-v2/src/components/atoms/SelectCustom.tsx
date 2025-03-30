import { MenuItem, Select } from "@mui/material";
import { useState } from "react";

const SelectCustom = ({
  values,
  defaultValue,
  width = "20%",
  height,
  onChange,
}: {
  onChange?: (value: string) => void;
  width?: number | string | undefined;
  height?: number | string | undefined;
  values: string[];
  defaultValue: string;
}) => {
  const [value, setValue] = useState<string>(defaultValue);
  return (
    <Select
      value={value}
      onChange={(change) => {
        setValue(change.target.value);
        onChange && onChange(change.target.value);
      }}
      sx={{ border: "solid black 1px", borderRadius: 3, width, height }}
    >
      {values.map((value) => (
        <MenuItem key={value} value={value}>
          {value}
        </MenuItem>
      ))}
    </Select>
  );
};

export default SelectCustom;
