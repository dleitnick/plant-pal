<template>
  <div id="forum-post-page">
    <div id="forum-post">
      <div id="forum-post-header">
        <div id="forum-header-citation">
          <img :src="avatar"/>
          <p id="poster" @click="goToUserProfile()">{{ post.user.username }}</p>
          <p>at</p>
          <p id="posted-date">{{ formatTimestamp(post.date) }}</p>
          <p>in</p>
          <p id="forum-location" @click="goToForum()">{{ forum.name }}</p>
        </div>
        <h2>{{ post.title }}</h2>
      </div>
      <p id="post-message" v-html="sanitizedMessage"></p>
      <post-to-thread :parentId="post.id" @thread-added="getThreads()"/>
    </div>
    <thread v-for="thread in threads" :key="thread.id" :thread="thread" />
  </div>
</template>

<script>
import forumService from "../services/ForumService.js";
import Thread from "../components/Thread.vue";
import moment from "moment";
import { faker } from '@faker-js/faker';
import PostToThread from "../components/PostToThread.vue";
import DOMPurify from "dompurify";

export default {
  name: "forum-post",
  components: {
    Thread,
    PostToThread,
  },
  data() {
    return {
      post: this.$store.state.currentForumPost,
      threads: [],
      forum: this.$store.state.currentForum,
      avatar: faker.image.avatar()
    };
  },
  computed: {
    sanitizedMessage() {
      return DOMPurify.sanitize(this.post.message);
    }
  },
  created() {
    // console.log("Calling getThreads()");
    this.getThreads();
  },
  methods: {
    getThreads() {
      // console.log(`Grabbing children for post id: ${this.post.id}`);
      forumService
        .getThreadsByParent(this.post.id)
        .then((response) => {
          this.threads = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    formatTimestamp(timestamp) {
      return moment(timestamp).calendar();
    },
    goToUserProfile() {
      this.$router.push({
        name: "userProfile",
        params: { username: this.post.user.urlSlug },
      });
    },
    goToForum() {
      this.$router.push({
        name: "forumSpecific",
        params: { name: this.forum.urlSlug },
      });
    },
  },
};
</script>

<style>

#forum-post-page {
  width: 60%;
  min-width: 600px;
  margin: auto;
}

#forum-header-citation {
  display: flex;
  gap: 0.3em;
  justify-content: left;
  align-items: center;
  background-color: #1A1C1D;
  padding: 0 0 0 10px;
  border-radius: 6px 6px 0 0;
  border-bottom: 1px solid #282927;
}

#forum-header-citation > img {
  width: 32px;
  height: 32px;
  margin-right: 10px;
}

#forum-post-header > h2 {
  font-family: Roboto;
  font-weight: bold;
  font-size: 4rem;
  margin: 0;
  padding: 10px;
  text-align: left;
}
#forum-post {
  margin-bottom: 20px;
  color: rgb(200, 196, 189);
  background-color: #181a1bb4;
  border-radius: 6px;
}

#post-message {
  padding: 10px;
  /* border-radius: 0 0 6px 6px; */
}

@media only screen and (max-width: 768px) {
  
}
</style>