import {createAsyncThunk, createSlice, isFulfilled, isRejected} from "@reduxjs/toolkit";
import {IUserInfo} from "../../interfaces/IUserInfo";
import {IUser} from "../../interfaces/IUser";
import {authService} from "../../services/authService";

interface IState {
    me: IUserInfo;
    error: boolean;
}
const initialState: IState = {
    me: null,
    error: null
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
            })
            .addCase(me.fulfilled, (state, action) => {
                state.me = action.payload;
            })
            .addMatcher(isRejected(login), state => {
                state.error = true;
            })
            .addMatcher(isFulfilled(login), state => {
                state.error = false;
            })
})

const {reducer: authReducer, actions} = authSlice;

const authActions = {
    ...actions,
    login,
    me
}
export {
    authActions,
    authReducer
}