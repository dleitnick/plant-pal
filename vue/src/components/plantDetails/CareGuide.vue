<template>
  <div id="plant-guide">
    <div class="divider" />
    <div id="plant-guide-container">
      <div
        v-for="(value, propertyName) in careGuide"
        :key="propertyName"
        :class="propertyName"
      >
        <h2 class="property-guide-header">{{ propertyName }}</h2>
        <p class="property-guide-value">{{ value }}</p>
      </div>
    </div>
    <div class="divider" />
  </div>
</template>



<script>
import plantService from "../../services/PlantsService.js";

export default {
  name: "care-guide",
  props: {
    plantId: {
      required: true,
    },
  },
  data() {
    return {
      careGuide: null,
    };
  },
  created() {
    this.fetchCareGuide();
  },
  methods: {
    fetchCareGuide() {
      plantService
        .getPlantCareGuide(this.plantId)
        .then((response) => {
          let guideResponse = response.data.data[0];
          this.careGuide = {
            watering: guideResponse.section[0].description,
            sunlight: guideResponse.section[1].description,
            pruning: guideResponse.section[2].description,
          };
        })
        .catch((error) => {
          console.error("Error fetching care guide:", error);
        });
    },
  },
};
</script>

<style>
#plant-guide > * > h2 {
  text-transform: capitalize;
}

#plant-guide > * > p {
  font-size: 1.45rem;
}

.show-care-guide-button {
  margin: 20px auto;
  padding: 10px 20px;
  height: 40px;
  background-color: #ffffff;
  color: black;
  border: none;
  border-radius: 5px;
  font-size: 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  transform: scale(1);
  transition: transform 0.3s ease-in-out;
}

.show-care-guide-button:hover {
  background-color: rgb(255, 208, 0);
  transform: scale(1.05);
}

.property-guide-header {
  text-transform: capitalize;
  margin: 7px;
  padding: 7px;
}

#plant-guide-container {
  display: flex;
  justify-content: space-evenly;
  max-width: 100vw;
  gap: 30px;
}

.property-guide-value {
  font-family: roboto;
  font-size: 22.5px;
  font-weight: 300; 
  text-align: center;   
  letter-spacing: .25px; 
}

.divider {
  border: none;
  border-top: 16.6px solid #629358;
  margin: 50px;
  border-radius: 100px;
}
</style>