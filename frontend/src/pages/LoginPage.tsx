import React from 'react';
import {SubmitHandler, useForm} from "react-hook-form";
import {IUser} from "../interfaces/IUser";
import {useAppDispatch, useAppSelector} from "../hooks/reduxHooks";
import {useNavigate} from "react-router-dom";
import {authActions} from "../redux/slices/authSlice";

const LoginPage = () => {
    const {register, handleSubmit} = useForm<IUser>();
    const {error} = useAppSelector(state => state.auth);
    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    const login:SubmitHandler<IUser> = async(user) => {
        const {meta: {requestStatus}} = await dispatch(authActions.login({user}));
        if(requestStatus === "fulfilled") {
            navigate("/worksheets");
        }
    };

    return (
        <form onSubmit={handleSubmit(login)}>
            <h1>Login page</h1>
            <input type="text" placeholder={"username"} {...register("username")}/>
            <input type="password" placeholder={"password"} {...register("password")}/>
            <button>Login</button>
            {error&& <div>Username or password is incorrect</div>}
        </form>
    );
};

export {LoginPage};