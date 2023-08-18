import axios from 'axios';

export default {

  getForums() {
    return axios.get('/forums');
  },

  getPostsByForum(forumId) {
    return axios.get(`/forums/${forumId}`);
  },

  getThreadsByParent(parentId){
    return axios.get(`/threads/${parentId}`);
  },

  createPost(thread, forumId) {
    return axios.post(`/forums/${forumId}`, thread);
  },

  createThread(thread, parentId) {
    return axios.post(`/threads/${parentId}`, thread)
  }

}