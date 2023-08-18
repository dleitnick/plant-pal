<template>
  <div @click="goToDetails()" class="search-result" v-show="!description == ''">
    <div class="search-result-name">
      <h3>
        <span class="capitalize">{{ plant.common_name }}</span>
      </h3>
      <p>
        <span class="label">Scientific Name:</span>
        <span class="capitalize" v-for="word in plant.scientific_name" :key="word"> {{ word }}</span>
      </p>
      <p>
        <span class="label">Indoor:</span>
        <span class="capitalize"> {{ indoor }}</span>
      </p>
    </div>
    <div class="search-result-image">
      <img v-if="imageExists" :src="plantObject.default_image.thumbnail" />
    </div>
    <div class="search-result-description">
      <p>{{ description }}</p>
    </div>
  </div>
</template>

<script>
import plantsService from '../services/PlantsService.js'

export default {
  name: "search-result",
  props: {
    plant: Object,
  },
  data() {
    return {
      plantObject: this.plant,
      imageExists: true,
      description: '',
      indoor: '',
    };
  },
  created() {
    if (
      this.plant.default_image == null ||
      Object.keys(this.plant).length == 0
    ) {
      this.plantObject.default_image = {};
      this.imageExists = false;
    }
    this.getPlantExtras();
  },
  methods: {
    goToDetails() {
      console.log("Navigating away");
      console.log(this.plant.id);
      this.$router.push({
        name: "plantDetails",
        params: { id: this.plant.id },
      });
    },
    getPlantExtras() {
      plantsService.getPlantDetails(this.plant.id)
      .then((response) => {
        this.description = response.data.description;
        this.indoor = response.data.indoor ? 'Yes' : 'No';
      })
      .catch((err) => {
        console.log(err);
      });
    }
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Caprasimo&family=Cormorant+Garamond:wght@300;600;700&family=Roboto:wght@100;300;400;500;700;900&display=swap");

h3 {
  font-size: 25px;
  font-family: "Caprasimo", cursive;
  font-weight: 10;
}

p {
  font-family: "roboto", serif;
  font-weight: 400;
  font-size: 18px;
}

.capitalize {
  text-transform: capitalize;
}

.label {
  font-weight: bold;
}

.search-result {
  display: grid;
  grid-template: 1fr 1fr;
  grid-template-areas: 
  "name image"
  "description description";
  max-width: 400px;
}
.search-result-name {
  grid-area: name;
}
.search-result-image {
  grid-area: image;
  text-align: right;
}
.search-result-description {
  grid-area: description;
}
</style>