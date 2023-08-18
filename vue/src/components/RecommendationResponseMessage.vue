<template>
  <div class="chat-message">
    <p style="white-space: pre-line;" v-if="!hasLocation()">{{ message.content }}</p>
    <p style="white-space: pre-line;" v-else>{{ newMessage }}</p>
  </div>
</template>

<script>
export default {
    name: 'recommendation-response-message',
    props: {
        message: Object,
    },
    data() {
        return {
            newMessage: '',
        }
    },
    created() {
    },
    methods: {
        hasLocation() {
            const hasLocation = this.message.content.startsWith("I live in ");
            if (hasLocation) {
                this.removeLocation();
            }
            return hasLocation;
        },
        removeLocation() {
            let str = this.message.content;
            const pos = str.indexOf('.');
            this.newMessage = str.substr(pos + 1).trim();
        }
    }
}
</script>

<style>
</style>