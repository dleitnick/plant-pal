import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  },

  getUser(id) {
    return axios.get(`/users/${id}`)
  },

  update(user) {
    return axios.put('/users', user);
  },

  getAllUsers() {
    return axios.get('/users');
  }
  
}
