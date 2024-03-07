import {createBrowserRouter, Navigate} from "react-router-dom";
import {MainLayout} from "./layouts/MainLayout";
import {RequiredAuth} from "./hoc/RequiredAuth";
import {WorksheetsPage} from "./pages/WorksheetsPage";
import {LoginPage} from "./pages/LoginPage";
import {RegisterPage} from "./pages/RegisterPage";

const router = createBrowserRouter([
    {
        path: '', element: <MainLayout/>, children: [
            {index: true, element: <Navigate to={"worksheets"}/>},
            {path: "worksheets", element: <RequiredAuth><WorksheetsPage/></RequiredAuth>},
            {path: "login", element: <LoginPage/>},
            {path: "register", element: <RegisterPage/>}
        ]
    }
])
export {
    router
}