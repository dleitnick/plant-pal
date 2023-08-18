<template>
  <div class="forum">
    <div class="forum-page" v-if="forum">
      <div id="forum-header">
        <h1>{{ forum.name }}</h1>
        <p>{{ forum.description }}</p>
        <post-to-thread :isPost="true" :parentId="forum.id" @post-added="getPosts()"/>
      </div>
      <div id="posts-list" v-if="posts.length > 0">
        <div class="post-link" v-for="post in posts" :key="post.id">
          <div class="post-link-citation">
            <img :src="getAvatar()" />
            <p id="poster" @click="goToUserProfile(post)">{{ post.user.username }}</p>
            <p>{{ formatTimestamp(post.date) }}</p>
          </div>
          <div class="post-link-body">
            <a href="" @click.prevent="goToPost(post)">{{ post.title }}</a>
            <p v-html="sanitizedMessage(post.message)"></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import forumService from "../services/ForumService.js";
import moment from "moment";
import { faker } from "@faker-js/faker";
import PostToThread from "../components/PostToThread.vue";
import DOMPurify from "dompurify";

export default {
  name: "forum",
  components: {
    PostToThread
  },
  data() {
    return {
      posts: [],
      forum: {},
    };
  },
  computed: {
    forums() {
      return this.$store.state.forums;
    },
    // forum() {
    //   return this.$store.state.forums.find((forum) => {
    //     console.log(
    //       `urlSlug: ${forum.urlSlug} == routeName: ${
    //         this.$route.params.name
    //       } ? ${forum.urlSlug == this.$route.params.name}`
    //     );
    //     return forum.urlSlug == this.$route.params.name;
    //   });
    // },
  },
  methods: {
    getPosts() {
      // console.log(`Grabbing posts for Forum ID: ${this.forum.id}`);
      forumService
        .getPostsByForum(this.forum.id)
        .then((response) => {
          this.posts = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    goToPost(post) {
      this.$store.commit("SET_CURRENT_FORUM_POST", post);
      this.$router.push({
        name: "forumPost",
        params: { name: this.forum.urlSlug, postTitle: post.urlSlug },
      });
    },
    goToUserProfile(post) {
      this.$router.push({
        name: "userProfile",
        params: { username: post.user.urlSlug },
      });
    },
    getAvatar() {
      return faker.image.avatar();
    },
    formatTimestamp(timestamp) {
      return moment(timestamp).calendar();
    },
    sanitizedMessage(message) {
      return DOMPurify.sanitize(message);
    }
  },
  watch: {
    forums: {
      immediate: true,
      handler() {
        this.forum = this.$store.state.forums.find((forum) => {
          // console.log(
          //   `urlSlug: ${forum.urlSlug} == routeName: ${
          //     this.$route.params.name
          //   } ? ${forum.urlSlug == this.$route.params.name}`
          // );
          return forum.urlSlug == this.$route.params.name;
        });
        if (this.forum) {
          this.$store.commit("SET_CURRENT_FORUM", this.forum);
          this.getPosts();
        }
      },
    },
  },
};
</script>

<style>
.forum-page {
  width: 60%;
  min-width: 600px;
  margin: auto;
  color: rgb(200, 196, 189);
}

#forum-header {
  background-color: #181a1bb4;
  font-weight: 300;
  padding: 10px;
  text-align: center;
}

.post-link {
  background-color: #181a1bb4;
  margin: 10px 0 10px 0;
}

.post-link-citation {
  display: flex;
  gap: 0.3em;
  justify-content: left;
  align-items: center;
  background-color: #1a1c1d;
  padding: 0 0 0 10px;
  border-radius: 6px 6px 0 0;
  border-bottom: 1px solid #282927;
}

.post-link-citation > img {
  width: 32px;
  height: 32px;
  margin-right: 10px;
}

.post-link > .post-link-body > a {
  font-family: Roboto;
  font-size: 2rem;
}

.post-link > .post-link-body > a:link {
  text-decoration: none;
}

.post-link > .post-link-body > a:hover {
  color: orange !important;
}

.post-link > .post-link-body > a:visited {
  color: inherit;
}

.post-link > .post-link-body > p {
  font-weight: 300;
  font-size: 1rem;
}

.post-link-body {
  padding: 10px;
}

#poster:hover, #forum-location:hover {
  color: orange;
  cursor: pointer;
}

@media only screen and (max-width: 768px) {
  
}
</style>