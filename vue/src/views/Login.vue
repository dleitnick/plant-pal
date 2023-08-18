<template>
  <div id="login" class=login-container>
    <form @submit.prevent="login" class="login-form">
      <h1 class="login-title">Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials" class="login-error">
        Invalid username and/or password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username" class="input-label">Username</label>
        <input
          type="text"
          id="username"
          v-model="user.username"
          class="input-field"
          required
          autofocus
        />
      </div>
      <div class="form-input-group">
        <label for="password" class="input-label">Password</label>
        <input type="password" id="password" v-model="user.password" class="input-field" required />
      </div>
      <button type="submit" class="submit-button">Login</button>
      <p class="signup-link">
        <router-link :to="{ name: 'register' }"
          >Need an account? Sign up here.</router-link
        >
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
.login-container{
  font-family: 'roboto', serif;
  display: flex; 
  justify-content: center; 
  align-items: center;
}
.login-title {
  font-size: 65px;
  margin-bottom: 1.0rem;
  text-align: left; 
  
}
.login-error {
  margin-bottom: 1rem;
  padding: 0.5rem;
  background-color: #f8d7da; 
  color: #721c24; 
  border-radius: 10px;
  font-size: 30px; 
  width: 80%;
}
.form-input-group {
  margin-bottom: 1.25rem;
}

.input-label {
  font-size: 30px;
}

label {
  margin-right: 0.5rem;
}

.input-field {
  width: 80%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 30px;
}

.submit-button {
  background-color: #007bff;
  color: #fff;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  font-size: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.submit-button:hover {
  background-color: #0056b3;
}

.signup-link > a {
  font-size: 25px; 
}
.signup-link > a:link {
  text-align: left;
  margin-top: 1rem;
  text-decoration: none;
}

.signup-link > a:visited {
  color: inherit;
  text-decoration: none;
}

.signup-link:hover {
    color: #ffc328;
}

@media only screen and (max-width: 768px) {
  
}
</style>