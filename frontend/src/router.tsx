import {createBrowserRouter, Navigate} from "react-router-dom";
import {MainLayout} from "./layouts/MainLayout";
import {RequiredAuth} from "./hoc/RequiredAuth";
import {WorksheetsPage} from "./pages/WorksheetsPage";
import {LoginPage} from "./pages/LoginPage";
import {RegisterPage} from "./pages/RegisterPage";
import {CreateWorksheet} from "./components/Worksheets/CreateWorksheet";
import {WorksheetDetails} from "./components/Worksheets/WorksheetDetails";

const router = createBrowserRouter([
    {
        path: '', element: <MainLayout/>, children: [
            {index: true, element: <Navigate to={"worksheets"}/>},
            {path: "worksheets", element: <RequiredAuth><WorksheetsPage/></RequiredAuth>},
            {path: "createWorksheet", element: <CreateWorksheet/>}, //this is component
            {path: "worksheetInfo", element: <WorksheetDetails/>},
            {path: "login", element: <LoginPage/>},
            {path: "register", element: <RegisterPage/>}
        ]
    }
])
export {
    router
}