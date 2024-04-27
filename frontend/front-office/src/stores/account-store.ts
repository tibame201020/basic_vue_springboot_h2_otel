import { defineStore } from "pinia";
import axiosUtil from "../utils/axios";

const baseUrl = "http://localhost:8089/api";

const useAccountStore = defineStore("accountStore", {
  state: () => ({
    user_info: {
      email:'',
      active:'',
      roleList:[]
    },
    access_token: "",
    res_msg: "",
    isLogin: false,
    email: "",
    password: ""
  }),
  actions: {
    login(): void {
      axiosUtil
        .post(
          `${baseUrl}/login`,
          {
            account: this.email,
            password: this.password,
          },
          {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded",
            },
          }
        )
        .then((res) => {
          const accessToken = res.headers["access_token"];
          this.access_token = accessToken;
          this.user_info = res.data;
          this.isLogin = true
        })
    },
    sendRequest(api: string): void {
      axiosUtil.post(`${baseUrl}${api}`).then((res) => {
        this.res_msg = res.data;
      })
    },
  },
})

export default useAccountStore;
