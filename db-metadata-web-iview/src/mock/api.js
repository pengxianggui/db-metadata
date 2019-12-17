import MockAdapter from 'axios-mock-adapter'
import axios from "@/axios";
import {URL} from '@/constant'
import commonRoutes from '@/router/commonRoute'

let mockAxios = new MockAdapter(axios);

mockAxios.onGet(URL.ROUTE_DATA).reply(200, {
    data: commonRoutes,
    state: 'ok'
});