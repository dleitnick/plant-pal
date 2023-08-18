<template>
  <div id="app">
    <div id="header-and-content">
      <div id="app-header" :class="{ shrink: atRecommendations }">
        <div id="header-top">
          <div class="auth-buttons">
            <div id="links-for-logged-in" v-if="userLoggedIn">
              <img
                id="profile-image"
                :src="profileImage"
                title="My Profile"
                @click="goToMyProfile()"
              />
              <router-link to="/logout" class="auth-button">Logout</router-link>
            </div>
            <div id="links-for-logged-out" v-else>
              <router-link to="/login" class="auth-button">Login</router-link>
              <span class="auth-divider">|</span>
              <router-link to="/register" class="auth-button"
                >Sign up</router-link
              >
            </div>
          </div>
        </div>
        <div id="header-nav">
          <div class="link-group" id="link-group-l">
            <router-link class="header-buttons" v-bind:to="{ name: 'forums' }">
              <a class="header-buttons">Forums</a>
            </router-link>
            <router-link
              class="header-buttons"
              v-bind:to="{ name: 'recommendations' }"
            >
              <a class="header-buttons">Garden Guru</a>
            </router-link>
          </div>
          <img
            src="./assets/imgs/plant_pal.png"
            alt="Plant Pal Logo"
            title="Home"
            @click="goToHome()"
          />
          <div class="link-group" id="link-group-r">
            <router-link
              class="header-buttons"
              v-bind:to="{
                name: 'virtualGarden',
                params: { id: this.$store.state.user.id },
              }"
            >
              <a class="header-buttons">Virtual Garden</a>
            </router-link>
            <router-link class="header-buttons" v-bind:to="{ name: 'about' }">
              <a class="header-buttons">About</a>
            </router-link>
          </div>
        </div>
        <div id="header-bottom">
          <plant-search v-if="!atHomePage" />
        </div>
      </div>
      <div id="content" :class="{ guru: atRecommendations }">
        <router-view />
      </div>
    </div>
    <div id="footer"></div>
  </div>
</template>

<script>
import googleMapsService from "./services/GoogleMapsService.js";
import PlantSearch from "./components/PlantSearch.vue";
import gardenService from "./services/GardenService.js";
import forumService from "./services/ForumService.js";
import authService from "./services/AuthService.js";
import { faker } from "@faker-js/faker";

export default {
  components: { PlantSearch },
  data() {
    return {
      currentPosition: {},
      options: {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0,
      },
      garden: [],
      gardenCareGuide: [],
      chatMessage: {
        content: "",
        function_call: null,
        name: null,
        role: "user",
      },
      chatMessagesInput: [],
      careSchedule: [],
      location: "",
      profileImage: faker.image.avatar(),
    };
  },
  computed: {
    userLoggedIn() {
      return Object.keys(this.$store.state.user).length !== 0;
    },
    atHomePage() {
      return this.$route.name === "home";
    },
    atRecommendations() {
      return this.$route.name === "recommendations";
    },
  },
  created() {
    this.currentPosition = navigator.geolocation.getCurrentPosition(
      this.success,
      this.error,
      this.options
    );
    if (this.$store.state.garden && this.$store.state.garden.length > 0) {
      this.garden = this.$store.state.garden;
    } else {
      this.getPlants();
    }
    this.getForums();
    this.$store.dispatch("fetchUsers");
  },
  methods: {
    success(pos) {
      const crd = pos.coords;
      googleMapsService.getLocation(crd).then((response) => {
        let addressArr = response.data.results[0].formatted_address.split(",");
        this.location = `${addressArr[1]},${addressArr[2]}`.trim();
        this.$store.commit("SET_LOCATION", this.location);
        if (this.location != "") {
          let userToUpdate = {
            username: this.$store.state.user.username,
            id: this.$store.state.user.id,
            email: this.$store.state.user.email,
            location: this.location,
            skillLevel: this.$store.state.user.skillLevel,
          };
          authService
            .update(userToUpdate)
            .then((response) => {
              if (response.status === 202) {
                console.log("User's location added to database.");
              }
            })
            .catch((err) => {
              console.log(err);
            });
        }
      });
    },
    error(err) {
      console.warn(`ERROR(${err.code}): ${err.message}`);
    },
    goToHome() {
      this.$router.push({ name: "home" });
    },
    getPlants() {
      console.log("Getting garden plants...");
      gardenService
        .getGardenByUser(this.$store.state.user.id)
        .then((response) => {
          this.garden = response.data;
          this.$store.commit("SET_GARDEN", this.garden);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getForums() {
      console.log("Getting forums...");
      forumService
        .getForums()
        .then((response) => {
          let forums = response.data;
          console.log(forums);
          this.$store.commit("SET_FORUMS", forums);
        })
        .catch((err) => {
          if (err.response && err.response.status === 401) {
            console.log(err);
            this.$store.commit("LOGOUT");
            this.$router.push("/login");
          }
          console.log(err);
        });
    },
    goToMyProfile() {
      this.$router.push({
        name: "userProfile",
        params: { username: this.$store.state.user.urlSlug },
      });
    },
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Caprasimo&family=Cormorant+Garamond:wght@300;600;700&family=Roboto:wght@100;300;400;500;700;900&display=swap");

html {
  color: ghostwhite;
}

body {
  width: 100vw;
  margin: auto;
  overflow-x: hidden;
}

#header-and-content {
  background-image: linear-gradient(
    180deg,
    #010e20 20%,
    rgba(11, 93, 93, 1) 100%
  );
}

#app-header {
  display: grid;
  width: 100vw;
  height: 200px;
  position: fixed;
  top: 0;
  z-index: 2;
  grid-template-columns: 1fr;
  grid-template-areas:
    "header-top"
    "header-nav"
    "header-bottom";
  grid-template-rows: 25% 50% 25%;
}

#app-header.shrink {
  height: 100px;
  width: 100%;
}

#app-header.shrink > #header-nav > img {
  margin-top: 90px;
  width: 200px;
  order: -1;
}

#header-top {
  grid-area: header-top;
  display: flex;
  justify-content: flex-end;
  padding: 20px;
}

#header-nav {
  grid-area: header-nav;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #629358;
}

#header-nav > img {
  width: 200px;
  cursor: pointer;
  transform: scale(1);
  /* Set the initial scale */
  transition: transform 0.3s ease-in-out;
  /* Add a smooth transition effect */
}

#header-nav > img:hover {
  transform: scale(1.05);
  /* Increase the scale value for the hover effect */
}

#header-bottom {
  grid-area: header-bottom;
  display: flex;
  justify-content: flex-end;
  padding-right: 40px;
}

#plant-search {
  margin-top: 10px;
}

.header-buttons:hover {
  color: #ffc328;
  transform: scale(1.05);
}

.header-buttons {
  text-align: center;
  transition: color 0.3s;
  transform: scale(1);
  transition: transform 0.3s ease-in-out;
}

.auth-button {
  font-family: "Caprasimo", sans-serif;
  font-weight: normal;
  font-size: 18px;
  color: white;
  text-decoration: none;
  margin: 0 10px;
  transition: color 0.3s;
  position: relative;
  z-index: 999;
}

.auth-divider {
  font-family: "Caprasimo", sans-serif;
  font-weight: normal;
  font-size: 18px;
  color: white;
  position: relative;
  z-index: 998;
}

#app-header.shrink > #header-top > .auth-buttons {
  margin-top: 19px;
}

.auth-button:hover {
  color: #ffc328;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

#content {
  min-height: 450px;
  flex-grow: 1;
  margin-top: 200px;
  padding: 10px 30px 30px 30px;
  /* background-image: linear-gradient(180deg,
      #010e20 20%,
      rgba(11, 93, 93, 1) 100%); */
}

#content.guru {
  margin-top: 75px;
  padding-top: 0;
}

#footer {
  width: 100vw;
  background-image: url("./assets/imgs/grassy_wildflowers_medium.png");
  background-size: cover;
  height: 700px;
  color: white;
  text-align: center;
  padding: 10px;
}

#header-nav > .link-group > a {
  font-family: "Caprasimo", sans-serif;
  font-weight: 10;
  font-size: 2.5rem;
}

#header-nav > .link-group > a:visited {
  text-decoration: none;
  color: inherit;
}

#header-nav > .link-group > a:link {
  text-decoration: none;
  color: inherit;
}

#app-header.shrink > #header-nav > .link-group {
  display: flex;
  max-width: 450px;
  justify-content: center;
  margin: 0;
  gap: 75px;
}

#app-header.shrink > #header-nav > #link-group-l {
  margin-left: -20px;
}

#app-header.shrink > #header-nav > .link-group > a {
  font-size: 1.5rem;
}

#app-header.shrink > #header-nav {
  display: flex;
  justify-content: center;
}

.link-group {
  display: flex;
  flex: 1;
  justify-content: space-around;
  gap: 75px;
}

#link-group-l {
  margin-left: 50px;
  justify-content: flex-end;
  margin-right: 75px;
}

#link-group-r {
  margin-right: 50px;
  justify-content: flex-start;
  margin-left: 75px;
}

#profile-image {
  width: 23px;
  height: 23px;
  position: relative;
  z-index: 999;
  transition: transform 0.3s ease-in-out;
  cursor: pointer;
  border-radius: 4px;
}

#profile-image:hover {
  transform: scale(1.3);
  cursor: pointer;
}

#links-for-logged-in {
  display: flex;
  justify-content: right;
}

@media only screen and (max-width: 768px) {
  
}
</style>
