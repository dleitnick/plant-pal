<template>
  <div class="thread" :style="threadMargin">
    <div class="thread-content">
      <div class="thread-header">
        <img :src="avatar" />
        <p id="poster" @click="goToUserProfile()">{{ thread.user.username }}</p>
        <p>{{ formatTimestamp(thread.date) }}</p>
      </div>
      <div class="thread-body">
        <p v-html="sanitizedMessage"></p>
      </div>
      <post-to-thread :parentId="thread.id" @thread-added="getThreads()"/>
    </div>
    <thread
      v-for="thread in threads"
      :key="thread.id"
      :thread="thread"
      :depth="depth + 1"
    />
  </div>
</template>

<script>
import moment from "moment";
import forumService from "../services/ForumService.js";
import PostToThread from "./PostToThread.vue";
import { faker } from "@faker-js/faker";
import DOMPurify from "dompurify";

export default {
  name: "thread",
  props: {
    thread: Object,
    depth: {
      type: Number,
      default: 0,
    },
  },
  components: {
    PostToThread,
  },
  data() {
    return {
      threads: [],
      avatar: faker.image.avatar(),
    };
  },
  created() {
    // console.log("Calling getThreads()");
    this.getThreads();
  },
  computed: {
    threadMargin() {
      return {
        "margin-left": `${this.depth * 40}px`,
      };
    },
    sanitizedMessage() {
      return DOMPurify.sanitize(this.thread.message);
    }
  },
  methods: {
    getThreads() {
      //   console.log(`Grabbing children for thread id: ${this.thread.id}`);
      forumService
        .getThreadsByParent(this.thread.id)
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
        params: { username: this.thread.user.urlSlug },
      });
    },
  },
};
</script>

<style>
.thread-content {
  background-color: #181a1bb4;
  border-radius: 6px;
  margin-bottom: 10px;
  color: rgb(200, 196, 189);
}

.thread-header {
  display: flex;
  gap: 0.3em;
  justify-content: left;
  align-items: center;
  background-color: #1a1c1d;
  padding: 0 0 0 10px;
  border-radius: 6px 6px 0 0;
  border-bottom: 1px solid #282927;
}

.thread-header > p {
  font-family: Roboto;
}

.thread-header > img {
  width: 32px;
  height: 32px;
  margin-right: 10px;
}

.thread-body {
  padding: 10px;
}

.thread-body > p {
  font-family: Roboto;
}
</style>