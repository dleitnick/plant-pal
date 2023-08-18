<template>
  <div id="review-list">
    <review v-for="review in reviews" :key="review.id" :review="review"/>
  </div>
</template>

<script>
// import reviewService from "../services/ReviewService.js";
import Review from "../plantDetails/Review.vue";

export default {
  name: "review-list",
  components: {
    Review,
  },
  props: {
    reviews: Array,
  },
};
</script>

<style>
</style>