import axios from "axios";

const api = axios.create({
    baseURL: process.env.REACT_APP_SERVER_ADDRESS,
    timeout: 1000,
});

api.interceptors.request.use(function (config) {
    const token = JSON.parse(localStorage.getItem('user') || "").token;
    config.headers.Authorization = `Bearer ${token}`;
    console.log(config);

    return config;
});

export {
    api
}
