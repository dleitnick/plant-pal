<template>
  <div id="recommendation-input">
    <form @submit.prevent="handleInput">
      <textarea id="textbox" rows="4" style="resize: none;"
        placeholder="Enter any information relevant to your recommendations." v-model="textInput"
        @keydown.enter.prevent="handleInput" />
      <div id="chat-submission">
        <span class="material-symbols-outlined" @click.prevent="handleReset">
          restart_alt
        </span>
        <span class="material-symbols-outlined" @click.prevent="handleInput">
          send
        </span>
        <input type="submit" value="Search" style="display:none;" />
      </div>
    </form>
  </div>
</template>

<script>
// import googleMapsService from "../services/GoogleMapsService.js";
import recommendationService from "../services/RecommendationService.js";

export default {
  name: "recommendation-input",
  data() {
    return {
      textInput: "",
      chatMessage: {
        content: '',
        function_call: null,
        name: null,
        role: 'user',
      },
      chatMessagesInput: [],
    };
  },
  computed: {
    location() {
      return this.$store.state.location;
    },
    chatMessages() {
      return this.$store.state.chatMessages;
    }
  },
  created() {
    this.chatMessagesInput = this.chatMessages;
  },
  methods: {
    handleInput() {
      if (this.chatMessages.length === 0) {
        let myLocation = this.location === '' ? 'USA probably, but prompt me for my location if needed' : this.location;
        this.chatMessage.content = `I live in ${myLocation} and my gardening level is: ${this.gardeningLevel()}. ${this.textInput}`;
      } else {
        this.chatMessage.content = this.textInput;
      }
      this.chatMessagesInput.push(this.chatMessage);
      this.$store.commit("SET_CHAT_MESSAGES", this.chatMessagesInput);
      if (this.chatMessages.length === 1) {
        this.$store.commit("SET_CHAT_MESSAGES", [this.chatMessage, this.chatMessage]);
      }
      this.textInput = '';
      this.$store.commit("SET_CHAT_MESSAGE_FETCHED", true);
      recommendationService
        .getRecommendation(this.chatMessagesInput)
        .then((response) => {
          this.chatMessagesInput = response.data;
          this.$store.commit("SET_CHAT_MESSAGES", this.chatMessagesInput);
          this.$store.commit("SET_CHAT_MESSAGE_FETCHED", false);
          this.$store.commit("SET_FETCH_RECOMMENDED_PLANTS", true);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handleReset() {
      console.log('resetting chat messages...')
      this.chatMessagesInput = [];
      this.$store.commit("SET_CHAT_MESSAGES", []);
      this.textInput = '';
    },
    gardeningLevel() {
      const gardeningLevel = this.$store.state.user.skillLevel;
      if (gardeningLevel === 3) return 'expert';
      else if (gardeningLevel === 2) return 'intermediate';
      else return 'novice';
    }
  },
};
</script>

<style></style>