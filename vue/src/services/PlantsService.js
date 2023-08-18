import axios from 'axios';

const baseURL = process.env.VUE_APP_PLANTS_API;
const http = axios.create({baseURL: baseURL});

export default {
    getPlantDetails(id) {
        const params = `/species/details/${id}?key=${process.env.VUE_APP_PLANTS_API_KEY}`
        return http.get(params);
    },

    findPlantsByName(plantName) {
        const params = `/species-list?key=${process.env.VUE_APP_PLANTS_API_KEY}&q=${plantName}`
        return http.get(params);
    },

    getPlantCareGuide(id) {
        const params = `/species-care-guide-list?key=${process.env.VUE_APP_PLANTS_API_KEY}&species_id=${id}`;
        return http.get(params);
    }
}