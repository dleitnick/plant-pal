<template>
  <div id="plant">
    <plant-details @plant-loaded="loadPage"/>
    <!-- <button class="show-care-guide-button" @click="toggleCareGuide">
      {{ showCareGuide ? 'Hide Care Guide' : 'Show Care Guide' }}
    </button> -->
    <care-guide :plant-id="this.$route.params.id" v-if="detailsReady"/>
    <plant-reviews-and-comments v-if="detailsReady"/>
  </div>
</template>

<script>
import PlantDetails from '../components/plantDetails/PlantDetails.vue'
import PlantReviewsAndComments from '../components/plantDetails/PlantReviewsAndComments.vue';
import CareGuide from '../components/plantDetails/CareGuide.vue';

export default {
  name: 'plant-details-view',
  components: {
    PlantDetails,
    PlantReviewsAndComments,
    CareGuide
  },
  data() {
    return {
      detailsReady: false,
    };
  },
 methods: {
  loadPage() {
    this.detailsReady = true;
  }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Caprasimo&family=Cormorant+Garamond:wght@300;600;700&family=Roboto:wght@100;300;400;500;700;900&display=swap');

#plant-details {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas:
    "header header header"
    "plant-image plant-info plant-info";
  gap: 55px;
}

#plant-header {
  grid-area: header;
}

h1 {
  font-family: 'Caprasimo', cursive;
  font-weight: 10;
  font-size: 50px;
  text-align: center;
}

h3 {
  font-size: 20px;
}

p {
  font-family: 'roboto', serif;
  font-weight: 400;
  font-size: 18px;
}

#plant-image {
  grid-area: plant-image;
  height: auto;
}

#plant-image>img {
  padding: 20px;
  width: 100%;
}

#plant-info {
  grid-area: plant-info;
  display: flex;
  justify-content: space-evenly;
}

.plant-info:first-child {
  margin-right: 20px;
}

#plant-reviews {
  width: 40%;
  padding: 20px;
}

#plant-comments {
  width: 40%;
  padding: 20px;
}

#reviews-and-comments {
  display: flex;
  flex-wrap: wrap;
  align-content: flex-start;
  justify-content: center;
  margin-top: -50px; 
}

#plant{
  margin-left: 110px;
  margin-right: 110px; 
}

@media only screen and (max-width: 768px) {
  
}
</style>