import { AppBar, Toolbar } from "@mui/material";
import SearchBar from "../molecules/AppBar/SearchBar";
import LeftBar from "../molecules/AppBar/LeftBar";
import RightBar from "../molecules/AppBar/RightBar";

function AppBarCustom({ userID }: { userID?: string }) {
  return (
    <AppBar sx={appBarSTL}>
      <Toolbar
        sx={{
          justifyContent: "space-between",
          padding: 2,
        }}
      >
        <LeftBar userID={userID} />
        <SearchBar />
        <RightBar userID={userID} />
      </Toolbar>
    </AppBar>
  );
}
export default AppBarCustom;

const appBarSTL: React.CSSProperties = {
  backgroundColor: "white",
  position: "relative",
  justifyContent: "space-between",
  boxShadow: "none",
  borderBottom: "solid grey 1px ",
};
