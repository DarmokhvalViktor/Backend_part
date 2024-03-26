import React from 'react';
import {Outlet} from "react-router-dom";
import {Header} from "../components/Header/Header";

const MainLayout = () => {
    return (
        <div>
            MainLayout
            <Header/>
            <Outlet/>
        </div>
    );
};

export {MainLayout};