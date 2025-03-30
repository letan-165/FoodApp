import { TextField, InputAdornment } from "@mui/material";
import ButtonCustom from "../../atoms/ButtonCustom";
import SearchIcon from "@mui/icons-material/Search";

const SearchBar = () => {
  return (
    <TextField
      placeholder="Tìm món ăn hoăc nhà hàng....."
      size="medium"
      sx={{
        backgroundColor: "#F3F3F3",
        borderRadius: 2,
        width: 400,
        "& fieldset": {
          border: "none",
        },
      }}
      slotProps={{
        input: {
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon />
            </InputAdornment>
          ),
          endAdornment: (
            <InputAdornment position="end">
              <ButtonCustom text="Tìm kiếm" />
            </InputAdornment>
          ),
        },
      }}
    />
  );
};

export default SearchBar;
