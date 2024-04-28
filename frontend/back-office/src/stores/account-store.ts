import { defineStore } from "pinia";
import axiosUtil from "../utils/axios";
import { RequestRecord } from "../models/RequestRecord";

const baseUrl = import.meta.env.VITE_API_URL

const useAccountStore = defineStore("accountStore", {
  state: () => ({
    user_info: {
      email: "",
      active: "",
      roleList: [],
    },
    access_token: "",
    users: [],
    records: [] as RequestRecord[],
    isLogin: false,
    email: "",
    password: "",
  }),
  actions: {
    login(): void {
      axiosUtil
        .post(
          `${baseUrl}/backoffice/login`,
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
          this.isLogin = true;
        });
    },
    fetchAllUser(): void {
      axiosUtil.post(`${baseUrl}/backoffice/fetchAllUser`).then((res) => {
        this.users = [];
        this.records = [];
        this.users = res.data;
      });
    },
    async fetchAllRecords() {
      return axiosUtil.post(`${baseUrl}/backoffice/fetchAllRecords`).then((res) => {
        this.users = [];
        this.records = [];
        this.records = res.data;
      });
    },
  },
});

export default useAccountStore;
