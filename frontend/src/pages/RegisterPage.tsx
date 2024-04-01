import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {IUserRegistration} from "../interfaces/IUserRegistration";
import {authService} from "../services/authService";
import {ITokenPairResponse} from "../interfaces/ITokenPairResponse";
import {useAppDispatch} from "../hooks/reduxHooks";
import {authActions} from "../redux/slices/authSlice";

const RegisterPage = () => {
    const navigate = useNavigate();
    const {register, handleSubmit} = useForm<IUserRegistration>();
    const [registrationError, setRegistrationError] = useState("");
    const dispatch = useAppDispatch();
    const handleRegister = async (user: IUserRegistration) => {
        try {
            await dispatch(authActions.register({user}));
            navigate("/worksheets")
        } catch (error) {
            setRegistrationError("Error happened during registration");
        }
    };

    return (
        <form onSubmit={handleSubmit(handleRegister)}>
            <h1>Register page</h1>
            <input
                type="text"
                placeholder="Username"
                {...register("username")}
            />
            <input
                type="email"
                placeholder="Email"
                {...register("email")}
            />
            <input
                type="password"
                placeholder="Password"
                {...register("password")}
            />
            <button>Register</button>
            {registrationError && <div>{registrationError}</div>}
        </form>
    );
};

export {RegisterPage};