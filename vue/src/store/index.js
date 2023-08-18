import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import authService from '../services/AuthService.js';

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));
const usersGarden = JSON.parse(localStorage.getItem('garden'));
const usersGardenCareSchedule = JSON.parse(localStorage.getItem('careSchedule'))

let usersGardenExists = false;
if (usersGarden) usersGardenExists = true;

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    plantRecommendation: {},
    chatMessages: [],
    location: '',
    activePlant: {},
    garden: usersGarden,
    filteredGarden: [],
    careSchedule: usersGardenCareSchedule,
    gardenFetched: usersGardenExists,
    chatMessageFetched: false,
    fetchRecommendedPlants: false,
    recommendedPlants: [],
    forums: [],
    userProfile: {},
    virtualGarden: [],
    currentForumPost: {},
    users: [],
    currentForum: {},
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('garden');
      state.garden = [];
      localStorage.removeItem('careSchedule');
      state.careSchedule = [];
      state.plantRecommendation = {};
      state.chatMessages = [];
      state.recommendedPlants = [];
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_PLANT_RECOMMENDATION(state, recommendation) {
      state.plantRecommendation = recommendation;
    },
    SET_CHAT_MESSAGES(state, chatMessages) {
      state.chatMessages = chatMessages;
    },
    SET_LOCATION(state, location) {
      state.location = location;
    },
    SET_ACTIVE_PLANT(state, plant) {
      state.activePlant = plant;
    },
    SET_GARDEN(state, garden) {
      state.garden = garden;
      state.gardenFetched = true;
      localStorage.setItem('garden', JSON.stringify(garden));
    },
    SET_CARE_SCHEDULE(state, careSchedule) {
      console.log("Committing care schedule")
      state.careSchedule = careSchedule;
      localStorage.setItem('careSchedule', JSON.stringify(careSchedule));
    },
    SET_CHAT_MESSAGE_FETCHED(state, chatMessageFetched) {
      state.chatMessageFetched = chatMessageFetched;
    },
    SET_FETCH_RECOMMENDED_PLANTS(state, value) {
      state.fetchRecommendedPlants = value;
    },
    SET_RECOMMENDED_PLANTS(state, recommendedPlants) {
      state.recommendedPlants = recommendedPlants;
    },
    SET_FORUMS(state, forums) {
      state.forums = forums;
    },
    SET_USER_PROFILE(state, userProfile) {
      state.userProfile = userProfile;
    },
    SET_VIRTUAL_GARDEN(state, virtualGarden) {
      state.virtualGarden = virtualGarden;
    },
    SET_CURRENT_FORUM_POST(state, currentForumPost) {
      state.currentForumPost = currentForumPost;
    },
    SET_USERS(state, users) {
      state.users = users;
    },
    UPDATE_USER_FROM_USERS(state, updatedUser) {
      const index = state.users.findIndex((user) => user.id == updatedUser.id);
      if (index !== -1) {
        Vue.set(state.users, index, updatedUser);
      }
    },
    SET_CURRENT_FORUM(state, forum) {
      state.currentForum = forum;
    },
    SET_FILTERED_GARDEN(state, garden) {
      state.filteredGarden = garden;
    }
  },
  actions: {
    async fetchUserProfile({ commit }, userId) {
      try {
        const response = await authService.getUser(userId);
        commit('SET_USER_PROFILE', response.data);
      } catch (error) {
        console.error(error);
      }
    },
    async fetchVirtualGarden({ commit }) {
      try {
        const response = await axios.get('/api/user/virtual-garden');
        commit('SET_VIRTUAL_GARDEN', response.data);
      } catch (error) {
        console.error(error);
      }
    },
    async fetchUsers({ commit }) {
      try {
        const response = await authService.getAllUsers();
        commit('SET_USERS', response.data);
      } catch (error) {
        console.error(error);
      }
    }
  },
});