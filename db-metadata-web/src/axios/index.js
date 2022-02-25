import axios from 'axios'

let instance = axios.create({
    baseURL: '/meta'
});

export default instance
