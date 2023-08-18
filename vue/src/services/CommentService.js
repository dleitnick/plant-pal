import axios from 'axios';

export default {

  getCommentById(id) {
    return axios.get(`/comments/${id}`);
  },

  getCommentsByPlant(plantId) {
    return axios.get(`/plants/${plantId}/comments`);
  },

  postComment(comment){
    return axios.post('/comments', comment)
  },

}
