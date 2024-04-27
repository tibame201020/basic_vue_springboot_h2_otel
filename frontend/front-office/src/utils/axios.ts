import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import useAccountStore from '../stores/account-store'
import { ElNotification } from 'element-plus'


const config: AxiosRequestConfig = {
  headers: {
    'Content-Type': 'application/json',
    'credentials': 'include'
  }
};

const axiosUtil = axios.create(config)

// pre request handle
axiosUtil.interceptors.request.use(
  (config) => {
    config.headers['Accept'] = 'application/json';
    const { access_token } = useAccountStore();
    if (access_token) {
      config.headers['Authorization'] = `Bearer ${access_token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

//pre handle response
axiosUtil.interceptors.response.use(
  (response: AxiosResponse) => {
    const access_token = response.headers['access_token'];
    const accountStore = useAccountStore();
    if (access_token) {
      accountStore.access_token = access_token
    }
    if (typeof response.data === 'object') {
      ElNotification({
        message: JSON.stringify(response.data),
        type: 'success',
      })
    } else {
      ElNotification({
        message: `${response.data}`,
        type: 'success',
      })
    }
    return response;
  },
  (error) => {
    if (error.response.data) {
      ElNotification({
        title: `${error.response.data.code}_${error.response.data.message}`,
        message: `${error.response.data.traceId}`,
        type: 'error',
      })
    } else {
      ElNotification({
        title: 'Error',
        message: `${error.response.status}_${error.message}`,
        type: 'error',
      })
    }

    return Promise.reject(error);
  }
);

export default axiosUtil