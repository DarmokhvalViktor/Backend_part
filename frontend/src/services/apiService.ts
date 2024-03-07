import axios, {AxiosError} from "axios";
import {authService} from "./authService";
import {router} from "../router";

const apiService = axios.create({baseURL: "/api"});
let isRefreshing = false;
type IWaitList = () => void;
const waitList: IWaitList[] = [];
apiService.interceptors.request.use(request => {
    const accessToken = authService.getAccessToken();
    if (accessToken) {
        request.headers.Authorization = `Bearer ${accessToken}`
    }
    return request;
})

apiService.interceptors.response.use(response => {
    return response;
    },
    async (error: AxiosError) => {
        const originalRequest = error.config;
        console.log(originalRequest.url);

        if(error.response.status === 401) {
            if(!isRefreshing) {
                isRefreshing = true;
                try {
                    await authService.refresh();
                    runAfterRefresh();
                    isRefreshing = false;
                    return apiService(originalRequest);
                } catch (e) {
                    authService.deleteTokens();
                    isRefreshing = false;
                    router.navigate("/login?SessionExpired=true")
                    return Promise.reject(error);
                }
            }
            if(originalRequest.url === "auth/refresh") {
                return Promise.reject(error);
            }
            return new Promise(resolve => {
                subscribeToWaitList(() => {
                    console.log(originalRequest.url);
                    resolve(apiService(originalRequest));
                })
            })
        }
        return Promise.reject(error);
    }
)

const subscribeToWaitList = (cb: IWaitList): void => {
    waitList.push(cb);
}
const runAfterRefresh = (): void => {
    while(waitList.length) {
        const cb = waitList.pop();
        cb();
    }
}
export {
    apiService
}