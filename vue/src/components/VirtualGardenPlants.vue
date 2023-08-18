<template>
  <div id="virtual-garden-plants">
    <plant-card
      v-for="plant in garden"
      :key="plant.id"
      :plant="plant"
      :personalUserGarden="personalGarden"
      @remove-plant="removePlant"
    />
  </div>
</template>

<script>
import PlantCard from "./PlantCard.vue";
import gardenService from "../services/GardenService.js";

export default {
  components: { PlantCard },
  name: "virtual-garden-plants",
  data() {
    return {};
  },
  props: {
    garden: Array,
    personalGarden: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    removePlant(plantId) {
      console.log("Removing plant...");
      gardenService
        .removePlantFromGarden(plantId)
        .then((response) => {
          console.log(response);
          console.log("success!");
          let garden = this.$store.state.garden;
          console.log(garden);
          garden = garden.filter((plant) => plant.id !== plantId);
          console.log(garden);
          this.$store.commit("SET_GARDEN", garden);
          this.$emit("update-garden");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
#virtual-garden-plants {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 10px;
}
</style>
