import { useContext, useEffect, useState } from "react";
import { Box } from "@mui/material";
import HomePage from "../left/HomePage";
import ForgotPasswordPage from "../right/Viewer/ForgotPasswordPage";
import LoginPage from "../right/Viewer/LoginPage";
import SignUpPage from "../right/Viewer/SignUpPage";
import { ChangePage, PageContext } from "../../hooks/ChangePage";

const LeftBody = () => {
  const { pageLeft } = useContext(PageContext);
  return (
    <Box sx={{ width: "75%", borderRight: "solid 1px" }}>
      {pageLeft === "HomePage" && <HomePage />}
    </Box>
  );
};

const RightBody = () => {
  const { pageRight } = useContext(PageContext);

  return (
    <Box sx={{ width: "25%", padding: 2 }}>
      {pageRight === "LoginPage" && <LoginPage />}
      {pageRight === "SignUpPage" && <SignUpPage />}
      {pageRight === "ForgotPasswordPage" && <ForgotPasswordPage />}

      <Box
        component="img"
        src="https://tse1.mm.bing.net/th?id=OIP.r0X-CYZvvYM71ZdNU4LXQQHaE8&pid=Api&P=0&h=220"
        alt="food"
        sx={{ borderRadius: 2, width: "100%", marginTop: 10 }}
      />
    </Box>
  );
};

const ViewerHome = () => {
  localStorage.removeItem("userID");
  return (
    <ChangePage defaultPageLeft="HomePage" defaultPageRight="LoginPage">
      <LeftBody />
      <RightBody />
    </ChangePage>
  );
};

export default ViewerHome;
