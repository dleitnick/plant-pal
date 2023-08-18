<template>
  <div class="forums-page-list-wrapper">
    <div id="forums-page-list">
      <div class="forum-links-container">
        <div class="forum-links" v-for="forum in forums" :key="forum.id">
          <h2 @click="goToForum(forum.urlSlug)">
            {{ forum.name }}
          </h2>
          <p>{{ forum.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "forums",
  computed: {
    forums() {
      return this.$store.state.forums;
    },
  },
  methods: {
    goToForum(urlSlug) {
      this.$router.push({ name: "forumSpecific", params: { name: urlSlug } });
    },
  },
};
</script>

<style>
.forums-page-list-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}

#forums-page-list {
  gap: 0.3em;
  background-color: #181a1bb4;
  border-radius: 1rem;
}

.forum-links > h2 {
  cursor: pointer;
  margin: 20px;
  padding: 20px;
}

.forum-links > h2:hover{
  color: #ffc328;
}

.forum-links > p{
  text-align: center; 
  margin-top: -20px; 
  font-size: 1.19rem; 
}

@media only screen and (max-width: 768px) {
  
}
</style>