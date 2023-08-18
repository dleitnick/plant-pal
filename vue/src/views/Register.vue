<template>
  <div id="register" class="text-center">
    <form @submit.prevent="register" class="registration-form">
      <h1 class="registration-title">Create Account</h1>
      <div role="alert" v-if="registrationErrors" class="registration-error">
        {{ registrationErrorMsg }}
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
        <input
          type="password"
          id="password"
          v-model="user.password"
          class="input-field"
          required
        />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword" class="input-label"
          >Confirm Password</label
        >
        <input
          type="password"
          id="confirmPassword"
          v-model="user.confirmPassword"
          class="input-field"
          required
        />
      </div>
      <div class="form-input-group">
        <label for="email" class="input-label">Email</label>
        <input
          type="email"
          id="email"
          v-model="user.email"
          class="input-field"
          required
        />
      </div>
      <div class="form-input-group">
        <label for="skillLevel" class="input-label">Gardening Level</label>
        <select id="skillLevel" v-model="user.skillLevel" class="input-field">
          <option
            v-for="level in skillLevels"
            :key="level.value"
            :value="level.value"
          >
            {{ level.label }}
          </option>
        </select>
      </div>
      <button type="submit" class="create-account-button">Create Account</button>
      <p class="login-link">
        <router-link :to="{ name: 'login' }"
          >Already have an account? Login here.</router-link
        >
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        email: "",
        skillLevel: 1,
        role: "user",
      },
      skillLevels: [
        { label: "Novice", value: 1 },
        { label: "Intermediate", value: 2 },
        { label: "Expert", value: 3 },
      ],
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>

.text-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.registration-title{
  font-size: 65px;
  margin-bottom: 1.0rem; 
  text-align: left; 
}

.registration-error{
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
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 1.25rem;
}

.input-label {
  font-family: 'roboto', serif;
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
.create-account-button {
  background-color: #007bff;
  color: #fff;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  font-size: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.create-account-button:hover {
  background-color: #0056b3;
}

.login-link > a{
  font-size: 25px;
}

.login-link > a:link{
  text-align: left;
  margin-top: 1rem;
  text-decoration: none;
}

.login-link > a:visited{
  color: inherit;
  text-decoration: none;
}

.login-link:hover {
    color: #ffc328;
}

@media only screen and (max-width: 768px) {
  
}
</style>
