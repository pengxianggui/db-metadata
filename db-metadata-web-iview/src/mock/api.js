import {URL} from '@/constant'
import commonRoutes from '@/router/commonRoute'

import axios from "axios";
import MockAdapter from 'axios-mock-adapter'

let mockAxios = new MockAdapter(axios);
mockAxios.onGet(URL.ROUTE_DATA).reply(200, {
    data: commonRoutes,
    state: 'ok'
}).onAny().passThrough();