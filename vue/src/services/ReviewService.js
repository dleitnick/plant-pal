import axios from 'axios';

export default {

  getReviewById(id) {
    return axios.get(`/reviews/${id}`);
  },

  getReviewsByPlant(plantId) {
    return axios.get(`/plants/${plantId}/reviews`);
  },

  postReview(review){
    return axios.post('/reviews', review)
  },

}
