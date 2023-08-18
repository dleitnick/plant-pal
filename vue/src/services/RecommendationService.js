import axios from 'axios';

export default {

  getRecommendation(userMsg) {
    return axios.post('/recommendations', userMsg);
  },

  getCareGuide(userMsg) {
    return axios.post('/care-guide', userMsg);
  },

  getRecommendedPlants(recommendation) {
    return axios.post('/recommendations/plants', recommendation);
  }

}