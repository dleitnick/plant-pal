<template>
  <div id="response">
    <div class="message-wrapper">
      <div class="chat-message">
        <p>Hi, I'm the Garden Guru, your personal botany expert! Whether you're tending a lush
          garden or nurturing your first houseplant, I'm here to help. Share your indoor lighting conditions, or
          any other preferences, and I'll recommend the perfect plants for you. Ready to make your thumb a little
          greener?
        </p>
      </div>
    </div>
    <div class="message-wrapper" v-for="(message, index) in chatMessages" :key="index" v-show="displayMessage(index)">
      <recommendation-response-message :message="message" v-if="displayMessage(index)" />
    </div>
    <loading-balls v-if="chatMessageFetched" :color="'lightgray'"/>
  </div>
</template>
<script>
import LoadingBalls from './loaders/LoadingBalls.vue';
import RecommendationResponseMessage from './RecommendationResponseMessage.vue';

export default {
  name: 'recommendation-response',
  components: {
    RecommendationResponseMessage,
    LoadingBalls,
  },
  data() {
    return {

    }
  },
  computed: {
    chatMessages() {
      return this.$store.state.chatMessages;
    },
    lastMessage() {
      let lastIndex = this.chatMessages.length - 1;
      return this.chatMessages[lastIndex];
    },
    chatMessageFetched() {
      return this.$store.state.chatMessageFetched;
    }
  },
  methods: {
    scrollToBottom() {
      this.$nextTick(() => {
        const container = document.getElementById('response');
        container.scrollTop = container.scrollHeight;
      });
    },
    displayMessage(index) {
      let response = index > 0 || this.chatMessages.length === 1;
      return response;
    }
  },
  watch: {
    chatMessages: {
      immediate: true,
      handler() {
        this.scrollToBottom();
      }
    }
  }
}
</script>

<style></style>