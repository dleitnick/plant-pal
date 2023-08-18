<template>
  <div id="comment">
    <div class>
      <p></p>
    </div>
    <p>{{ comment.commentText }}</p>
    <div class="citation">
      <img :src="avatar"/>
      <p id="review-and-comments-linkable" @click="goToUserProfile()"> {{ comment.username }} </p>
      <p id="posted-date" class="date">{{ formatTimestamp(comment.dateTime) }}</p>
      <p>{{ comment.location }}</p>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import { faker } from '@faker-js/faker';

export default {
  name: "comment",
  props: {
    comment: Object,
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
        params: { username: this.comment.username.toLowerCase() },
      });
    },

  },
};
</script>

<style>

#review-and-comments-linkable:hover{
  cursor: pointer; 
  color: rgb(255, 208, 0);
}

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