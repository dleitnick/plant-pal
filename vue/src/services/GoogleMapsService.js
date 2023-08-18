import axios from 'axios';

const baseURL = process.env.VUE_APP_GOOGLE_MAPS_API;
const http = axios.create({ baseURL: baseURL });
http.defaults.headers = null;


export default {
    getLocation(latlng) {
        const params = `/geocode/json?latlng=${latlng.latitude},${latlng.longitude}&key=${process.env.VUE_APP_GOOGLE_MAPS_API_KEY}`;
        return http.get(params);
    }
}