<template>
  <div class="user-profile" v-if="userProfile">
    <h1>User Profile</h1>
    <div id="profile-avatar-and-info">
      <img :src="avatar"/>
    <div class="profile-info" v-if="userProfile">
      <p><strong>Username:</strong> {{ userProfile.username }}</p>
      <p><strong>Joined:</strong> {{ formatTimestamp(userProfile.created) }}</p>
      <p><strong>Location:</strong> {{ userProfile.location }}</p>
      <p><strong>Skill Level:</strong> {{ gardeningLevel }}</p>
    </div>
  </div>
    <div id="user-virtual-garden">
      <h1>{{ userProfile.username }}'s Garden</h1>
      <virtual-garden-plants :garden="userProfile.garden" />
    </div>
  </div>
</template>

<script>
import moment from "moment";
import gardenService from "../services/GardenService.js";
import VirtualGardenPlants from "../components/VirtualGardenPlants.vue";
import { faker } from '@faker-js/faker';

export default {
  components: { VirtualGardenPlants },
  data() {
    return {
      avatar: faker.image.avatar(),
    };
  },
  computed: {
    userProfile() {
      if (!this.$store.state.users) return '';
      return this.$store.state.users.find((user) => {
        // console.log(`urlSlug: ${user.urlSlug} == Router username: ${this.$route.params.username} ? ${user.urlSlug == this.$route.params.username}`);
        return user.urlSlug == this.$route.params.username;
      });
    },
    garden() {
      return this.userProfile.garden;
    },
    gardeningLevel() {
      return this.$store.state.user.skillLevel == 1
        ? "Novice"
        : this.$store.state.user.skillLevel == 2
        ? "Intermediate"
        : "Expert";
    },
  },
  created() {
    setTimeout(() => {
      console.log(`User exists ? ${this.userProfile}`);
      if (!this.userProfile.garden) {
        console.log(
          `Grabbing ${this.userProfile.username} (ID: ${this.userProfile.id}) garden...`
        );
      this.getGarden();
      }
    }, 100);
  },
  methods: {
    formatTimestamp(timestamp) {
      return moment(timestamp).calendar();
    },
    getGarden() {
      if (!this.userProfile) {
        return;
      }
      gardenService
        .getGardenByUser(this.userProfile.id)
        .then((response) => {
          let gardenResponse = response.data;
          let user = {...this.userProfile};
          user.garden = gardenResponse;
          this.$store.commit('UPDATE_USER_FROM_USERS', user);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  // watch: {
  //   userProfile(val) {
  //     if (val) {
  //       console.log(
  //         `Grabbing ${this.userProfile.username} (ID: ${this.userProfile.id}) garden...`
  //       );
  //       this.getGarden();
  //     }
  //   },
  // },
};
</script>

<style scoped>
.user-profile {
  padding: 20px;
  background-color: #181a1bb4;
  border-radius: 20px;
  text-align: left;
  max-width: 55vw;
  margin: 0 auto;
  margin-top: 80px;
}

.profile-info {
  margin-top: 20px;
  max-width: 300px;
}

h1 {
  font-size: 2.5rem;
  color: rgb(255, 255, 255);
  margin-top: -3px;
  text-align: left;
  margin-left: 67px; 
  text-transform: capitalize; 
}

p {
  font-size: 20px;
  color: rgb(255, 255, 255);
  margin-bottom: 5px;
  margin-left: 10px;
}

strong {
  color: rgb(255, 255, 255);
}

.gardening-level {
  color: #ff9800;
  font-weight: bold;
}

#user-virtual-garden{
  margin-top: 50px;
}

#virtual-garden-plants{
  display: flex; 
  justify-content: flex-start; 
  margin-left: 67px; 
}

#profile-avatar-and-info {
  display: flex;
  justify-content: flex-start;
  gap: 20px;
  margin-left: 67px; 
}

#profile-avatar-and-info > img {
  width: 200px;
  height: 200px;
  border-radius: 10px; 
}

@media only screen and (max-width: 768px) {
}

</style>