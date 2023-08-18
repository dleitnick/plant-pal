<template>
  <div id="review">
    <div class="rating">
      <span class="material-symbols-outlined" v-for="star in review.rating" :key="star"> star </span>
    </div>
    <p>{{ review.reviewText }}</p>
    <div class="citation">
      <img :src="avatar"/>
      <p id="review-and-comments-linkable" @click="goToUserProfile()"> {{ review.username }} </p>
      <p id="posted-date" class="date">{{ formatTimestamp(review.dateTime) }}</p>
      <p>{{ review.location }}</p>
    </div>
  </div>
</template>

<script>
import moment from 'moment';
import { faker } from '@faker-js/faker';

export default {
  name: "review",
  dateTime: '',
  props: {
    review: Object,
  },
  data() {
    return {
      avatar: faker.image.avatar()
    };
  },
  methods: {
    formatTimestamp(timestamp) {
      return moment(timestamp).calendar();
    },
    goToUserProfile() {
      this.$router.push({
        name: "userProfile",
        params: { username: this.review.username.toLowerCase()},
      });
  }
}
}
</script>

<style>
#review, #comment {
  margin: 10px;
  color: rgb(200, 196, 189);
  background-color: #181A1B;
  border-radius: 6px;
}

#review > p, #comment > p {
  padding: 10px 10px 0 10px;
}
.rating .material-symbols-outlined {
  color: rgb(255, 208, 0);
  font-variation-settings: "FILL" 1, "wght" 400, "GRAD" 0, "opsz" 48;
  padding: 10px 0 0 10px;
}

/* .citation {
  border-top: 1px solid #282927;
  background-color: #1A1C1D;
  display: flex;
  justify-content: flex-end;
  padding: 0 10px 0 0;
  border-radius: 0 0 6px 6px;

}
.citation > p {
  font-size: 0.9rem;
  margin-right: 5px;
} */

.citation {
  display: flex;
  gap: 0.3em;
  justify-content: left;
  align-items: center;
  background-color: #1A1C1D;
  padding: 0 0 0 10px;
  border-radius: 6px 6px 0 0;
  border-bottom: 1px solid #282927;
}

.citation > img {
  width: 32px;
  height: 32px;
  margin-right: 10px;
}

.date {
  margin-left: 10px; /* Adjust the value as needed */
}
</style>