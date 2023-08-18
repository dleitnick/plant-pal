<template>
  <div id="recommendation-view">
    <div id="recommendation-body">
      <div id="chat-box">
        <div class="image-container">
          <h1>Garden Guru</h1>
          <img src="../assets/imgs/garden_guru_small.png" alt="">
        </div>
        <recommendation-response />
        <recommendation-input />
      </div>
    </div>
    <recommended-plants/>
  </div>
</template>

<script>
import RecommendationInput from "../components/RecommendationInput.vue";
import RecommendationResponse from "../components/RecommendationResponse.vue";
import RecommendedPlants from "../components/RecommendedPlants.vue";

export default {
  name: 'recommendation-view',
  components: {
    RecommendationInput,
    RecommendationResponse,
    RecommendedPlants
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Calistoga&family=Caprasimo&family=Capriola&family=Cormorant+Garamond:wght@300;600;700&family=Patrick+Hand+SC&family=Roboto:wght@100;300;400;500;700;900&display=swap');

#recommendation-body {
  display: flex;
  justify-content: center;
}

#recommendation-body>img {
  width: 200px;
  height: auto;
  object-fit: cover;
}

#chat-box {
  width: 900px;
  overflow: hidden;
  position: relative;
}

.image-container {
  position: relative; /* Sets a reference point for the absolutely positioned h1 */
  height: 200px;
}

#chat-box h1 {
  position: absolute; /* Positions h1 independently of other elements */
  top: 15%; /* Adjust these values to move the h1 where you want it */
  left: 43%;
  z-index: 1; /* Ensures that the h1 appears above the image */
  color: white;
  text-shadow: 0px 0px 13px rgb(0, 0, 0);
  /* font-family: 'Roboto Condensed'; */
  font-weight: 200;
  font-size: 3.7rem;
  font-family: 'Caprasimo', sans-serif;
}

#chat-box img {
  width: 100%;
  height: 200px;
  object-position: top;
  object-fit: cover;
}

/* #chat-box {
  width: 900px;
  overflow: hidden;
}

#chat-box>img {
  width: 100%;
  height: 200px;
  object-position: top;
  object-fit: cover;
}

#chat-box>h1 {
  color: black;
  position: absolute;
  top: 30%;
  left: 60%;
  transform: translate(-50%, -50%);
} */

#response {
  background-color: #353535;
  /* background-color: #181a1bb4; */
  width: 900px;
  height: 500px;
  overflow-x: hidden;
  overflow-y: auto;
  /* padding: 10px; */
  display: flex;
  flex-direction: column;
  margin: auto;
}

.message-wrapper {
  color: black;
  max-width: 80%;
}

#response .message-wrapper:nth-child(odd) {
  align-self: flex-end;
  border-top: 10px solid #5fa5ac;
}

#response .message-wrapper:nth-child(even) {
  border-top: 10px solid rgb(98,147,88);
}

#response .message-wrapper:first-child {
  align-self: flex-start;
  border-top: 10px solid rgb(98,147,88);
}

#response > #ball-container {
  position: absolute;
  left: 12px;
  /* transform: translateX(-50%); */
  bottom: 130px;
  z-index: 9999;
}

.message-wrapper {
  border-radius: 10px;
  padding: 0 10px 0 10px;
  margin: 10px 15px 10px 15px;
  background-color: lightgray;
}

.message-wrapper:nth-child(2) {
  display: none;
}

#response::-webkit-scrollbar {
  width: 0.9em;
  /* for vertical scrollbars */
  height: 0.65em;
  /* for horizontal scrollbars */
}

#response::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
}

#response::-webkit-scrollbar-thumb {
  background: #629358;
}

#recommendation-input {
  margin: auto;
  max-width: 900px;
  background-color: #353535;
  /* background-color: #181a1bb4; */
  padding: 20px;
  border-radius: 0 0 15px 15px; 
}

#recommendation-input>form>#textbox {
  border-top: 8px solid #5fa5ac;
  border-radius: 10px;
  background-color: lightgray;
  padding: 5px;
  font-family: roboto;
  font-size: 1.1rem;
  width: 92%;
}

#recommendation-input>form>#textbox:focus {
  outline: none;
}

#recommendation-input>form {
  display: flex;
}

#chat-submission {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

#chat-submission .material-symbols-outlined {
  cursor: pointer;
  color: white;
  font-variation-settings: "FILL" 1, "wght" 400, "GRAD" 0, "opsz" 48;
  font-size: 2rem;
  padding-left: 15px;
}

#response * {
  overflow-anchor: none;
}

#anchor {
  overflow-anchor: auto;
  height: 1px;
}

@media only screen and (max-width: 768px) {
  
}
</style>