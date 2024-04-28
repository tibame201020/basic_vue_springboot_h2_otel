import http from 'k6/http';
import { sleep } from 'k6';
export const options = {
  vus: 10,
  duration: '5s',
};
export default function () {
  http.get('http://localhost:8089/api/publishApi');
  sleep(1);
}