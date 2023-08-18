<template>
  <div id="comment-list">
    <comment v-for="comment in comments" :key="comment.id" :comment="comment" />
  </div>
</template>

<script>
// import commentService from "../services/CommentService.js";
import Comment from "./CommentItem.vue";

export default {
  name: "comment-list",
  components: {
    Comment,
  },
  props: {
    comments: Array,
  },
  data() {
    return {};
  },
  created() {
    // this.getComments();
    // console.log(this.$route.params.id);
  },
  methods: {
    // getComments() {
    //   commentService
    //     .getCommentsByPlant(this.$route.params.id)
    //     .then((response) => {
    //       this.comments = response.data;
    //       console.log("Grabbing comments...");
    //       console.log(response.data);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
  },
};
</script>

<style></style>
