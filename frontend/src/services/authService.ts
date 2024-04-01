import {IUser} from "../interfaces/IUser";
import {IRes} from "../types/resType";
import {apiService} from "./apiService";
import {ITokenPairResponse} from "../interfaces/ITokenPairResponse";
import {IUserInfo} from "../interfaces/IUserInfo";
import {IUserRegistration} from "../interfaces/IUserRegistration";

const accessTokenKey = "accessToken";
const refreshTokenKey = "refreshToken";


const authService = {
    async register(user: IUserRegistration): Promise<IUserInfo> {
        const {data} = await apiService.post("auth/register", user);
        this.setTokens(data);
        const {data: me} = await this.me();
        return me;
    },
    async login(user: IUser): Promise<IUserInfo> {
        const {data} = await apiService.post("auth/login", user);
        this.setTokens(data);
        const {data: me} = await this.me();
        console.log(me)
        return me;
    },
    async refresh(): Promise<void> {
        const refreshToken = this.getRefreshToken();
        const {data} = await apiService.post("auth/refresh", {refreshToken: refreshToken});
        this.setTokens(data);
    },
    // TODO check it and implement, must return user credentials
    me(): IRes<IUserInfo> {
        return apiService.get("auth/me");
    },
    setTokens({accessToken, refreshToken}:ITokenPairResponse):void {
        localStorage.setItem(accessTokenKey, accessToken);
        localStorage.setItem(refreshTokenKey, refreshToken);
    },
    getAccessToken(): string {
        return localStorage.getItem(accessTokenKey);
    },
    getRefreshToken():string {
        return localStorage.getItem(refreshTokenKey);
    },
    deleteTokens(): void {
        localStorage.removeItem(accessTokenKey);
        localStorage.removeItem(refreshTokenKey);
    }
}

export {
    authService
}