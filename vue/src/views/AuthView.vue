<template>
    <div class="auth-message-container">
        <div class="auth-message">
            <p>Please <router-link to="/login" class="auth-link">login</router-link> or <router-link to="/register"
                    class="auth-link">create an account</router-link> to utilize this feature.</p>
        </div>
    </div>
</template>

<script>
export default {
    computed: {
        userAuthenticated() {
            return this.$store.state.token !== '';
        },
        title() {
            if (this.userAuthenticated) {
                const gardenOwner = this.$store.state.user;
                if (gardenOwner.id === this.$store.state.user.id) {
                    return "Your Virtual Garden";
                } else {
                    return `${gardenOwner.username}'s Virtual Garden`;
                }
            } else {
                return "Virtual Garden";
            }
        }
    }
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Caprasimo&family=Cormorant+Garamond:wght@300;600;700&family=Roboto:wght@100;300;400;500;700;900&display=swap");

.auth-message-container {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50vh;
}

.auth-message {
  padding: 15px;
  text-align: center;
  max-width: 1000px; /* Set a maximum width to control message width */
}

.auth-message p {
    font-family: 'Caprasimo', sans-serif;
    font-size: 50px; /* Adjust the font size to your preference */
    margin: 0; /* Remove default margin for the <p> element */
}

.auth-link {
  color: #007bff;
  text-decoration: none;
  margin: 0 5px;
  transition: color 0.3s; /* Add smooth transition for color change */
}

.auth-link:hover {
  color: #ffc328;
}
</style>