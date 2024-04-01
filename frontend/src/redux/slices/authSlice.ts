import {createAsyncThunk, createSlice, isFulfilled, isPending, isRejected} from "@reduxjs/toolkit";
import {IUserInfo} from "../../interfaces/IUserInfo";
import {IUser} from "../../interfaces/IUser";
import {authService} from "../../services/authService";
import {IUserRegistration} from "../../interfaces/IUserRegistration";

interface IState {
    me: IUserInfo;
    error: boolean;
    loading: boolean;
}
const initialState: IState = {
    me: null,
    error: null,
    loading: false
}

const login = createAsyncThunk<IUserInfo, {user: IUser}>(
    "authSlice/login",
    async ({user}, {rejectWithValue}) => {
        try {
            return await authService.login(user);
        } catch (e) {
            return rejectWithValue(e);
        }
    }
)
const register = createAsyncThunk<IUserInfo, {user:IUserRegistration}>(
    "authSlice/register",
    async({user}, {rejectWithValue}) => {
       try {
           return await authService.register(user);
       } catch (e) {
           return rejectWithValue(e);
       }
    }
)
const me = createAsyncThunk<IUserInfo, void>(
    "authSlice/me",
    async (_, {rejectWithValue}) => {
        try {
            const {data} = await authService.me();
            return data;
        } catch (e) {
            return rejectWithValue(e);
        }
    }
)
const authSlice = createSlice({
    name: "authSlice",
    initialState,
    reducers: {},
    extraReducers: builder =>
        builder
            .addCase(login.fulfilled, (state, action) => {
                state.me = action.payload;
                state.error = false;
                state.loading = false;
            })
            .addCase(register.fulfilled, (state, action) => {
                console.log("fulfilled register", action.payload);
                state.me = action.payload;
                state.error = false;
                state.loading = false;
            })
            .addCase(me.fulfilled, (state, action) => {
                state.me = action.payload;
                console.log("fulfilled me", action.payload);
                state.error = false;
                state.loading = false;
            })
            .addMatcher(isPending(login, me, register), state => {
                console.log("pending register, me or login");
                state.error = false;
                state.loading = true;
            })
            .addMatcher(isRejected(login, me, register), state => {
                state.error = true;
                state.loading = false;
            })
})

const {reducer: authReducer, actions} = authSlice;

const authActions = {
    ...actions,
    login,
    me,
    register
}
export {
    authActions,
    authReducer
}