<template>
  <div id="plant-details" v-if="plantLoaded">
    <div id="plant-header">
      <h1 class="capitalize">{{ removeAsterisk(plant.common_name)}}</h1>
      <div class="button-wrapper-add-to-garden" v-if="plantNotInGarden && gardenFetched">
        <button class="add-to-my-garden-button" @click.prevent="addToMyGarden()" v-if="!plantAdded">Add to my
          Garden</button>
      </div>
      <div class="button-wrapper-add-to-garden" v-if="!plantNotInGarden"><p>🌼 This plant is in your Virtual Garden 🌱</p></div>
      <div id="added-to-garden-message" v-if="showAddedMessage"><p>Added to your garden.</p></div>
      <p>
        <span class="label">Scientific Name:</span>
        {{ formatValue(formatArrayValue(plant.scientific_name)) }}
      </p>
      <p><span class="label">Description:</span> {{ plant.description }}</p>
    </div>
    <div id="plant-info">
      <div class="plant-info">
        <p>
          <span class="label">Cones:</span>
          {{ formatValue(formatBooleanValue(plant.cones)) }}
        </p>
        <p><span class="label">Cycle:</span> {{ formatValue(plant.cycle) }}</p>
        <p>
          <span class="label">Dimensions:</span>
          {{ formatValue(plant.dimension) }}
        </p>
        <p>
          <span class="label">Drought Tolerant:</span>
          {{ formatValue(formatBooleanValue(plant.drought_tolerant)) }}
        </p>
        <p>
          <span class="label">Edible Fruit:</span>
          {{ formatValue(formatBooleanValue(plant.edible_fruit)) }}
        </p>
        <p>
          <span class="label">Edible Leaf:</span>
          {{ formatValue(formatBooleanValue(plant.edible_leaf)) }}
        </p>
        <p>
          <span class="label">Family:</span> {{ formatValue(plant.family) }}
        </p>
        <p>
          <span class="label">Flower Color:</span>
          {{ formatValue(plant.flower_color) }}
        </p>
        <p>
          <span class="label">Flowers:</span>
          {{ formatValue(formatBooleanValue(plant.flowers)) }}
        </p>
        <p>
          <span class="label">Fruit Bearing:</span>
          {{ formatValue(formatBooleanValue(plant.fruits)) }}
        </p>
        <p>
          <span class="label">Growth Rate:</span>
          {{ formatValue(plant.growth_rate) }}
        </p>
        <p>
          <span class="label">Hardiness Zone:</span>
          {{ formatValue(formatHardinessValue(plant.hardiness)) }}
        </p>
        <p>
          <span class="label">Harvest Season:</span>
          {{ formatValue(plant.harvest_season) }}
        </p>
        <p>
          <span class="label">Indoor:</span>
          {{ formatValue(formatBooleanValue(plant.indoor)) }}
        </p>
        <p :class="{ invasive: plant.invasive }">
          <span class="label">Invasive:</span>
          {{ formatValue(formatBooleanValue(plant.invasive)) }}
        </p>
      </div>

      <div class="plant-info">
        <p>
          <span class="label">Leaf:</span>
          {{ formatValue(formatBooleanValue(plant.leaf)) }}
        </p>
        <p>
          <span class="label">Maintenance:</span>
          {{ formatValue(plant.maintenance) }}
        </p>
        <p>
          <span class="label">Medicinal:</span>
          {{ formatValue(formatBooleanValue(plant.medicinal)) }}
        </p>
        <p>
          <span class="label">Origin:</span>
          {{ formatValue(formatOriginValue(plant.origin)) }}
        </p>
        <p>
          <span class="label">Propagation:</span>
          {{ formatValue(formatArrayValue(plant.propagation)) }}
        </p>
        <p>
          <span class="label">Pruning Month:</span>
          {{ formatValue(formatArrayValue(plant.pruning_month)) }}
        </p>
        <p>
          <span class="label">Salt Tolerant:</span>
          {{ formatValue(formatBooleanValue(plant.salt_tolerant)) }}
        </p>

        <p><span class="label">Seeds:</span> {{ formatValue(plant.seeds) }}</p>
        <p>
          <span class="label">Sunlight:</span>
          {{ formatValue(formatArrayValue(plant.sunlight)) }}
        </p>
        <p>
          <span class="label">Thorny:</span>
          {{ formatValue(formatBooleanValue(plant.thorny)) }}
        </p>
        <p>
          <span class="label">Tropical:</span>
          {{ formatValue(formatBooleanValue(plant.tropical)) }}
        </p>
        <p><span class="label">Type:</span> {{ formatValue(plant.type) }}</p>
        <p>
          <span class="label">Watering:</span> {{ formatValue(plant.watering) }}
        </p>
        <p>
          <span class="label">Watering Period:</span>
          {{ formatValue(plant.watering_period) }}
        </p>
      </div>
    </div>
    <div id="plant-image">
      <img v-if="plant.default_image" :src="plant.default_image.original_url" alt="" />
    </div>
  
  </div>
</template>

<script>
import plantsService from "../../services/PlantsService.js";
import gardenService from "../../services/GardenService.js";

export default {
  components: {
  },
  name: "plant-details",
  data() {
    return {
      plant: {},
      reviews: [],
      comments: [],
      showCareGuide: false,
      plantAdded: false,
      showAddedMessage: false,
    };
  },
  created() {
    this.getPlant();
    if (!this.$store.state.gardenFetched) {
      this.getPlants();
    }
    this.$store.commit("SET_ACTIVE_PLANT", this.plant);
  },
  computed: {
    plantLoaded() {
      return Object.keys(this.plant).length !== 0;
    },
    gardenFetched() {
      return this.$store.state.gardenFetched;
    },
    plantNotInGarden() {
      console.log("Checking the garden")
      if (this.$store.state.garden.length === 0) {
        return true;
      } else {
        return !this.$store.state.garden.some(g => {
          // console.log(`Plant: ${g.id} == CurrentPlant: ${this.$route.params.id} ? ${g.id == this.$route.params.id}`)
          return g.id == this.$route.params.id
        });
      }
    },
  },
  methods: {
    formatBooleanValue(value) {
      return value ? "Yes" : "No";
    },
    getPlant() {
      plantsService
        .getPlantDetails(this.$route.params.id)
        .then((response) => {
          this.plant = response.data;
          this.checkPlantNulls();
          this.$emit('plant-loaded');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    addToMyGarden() {
      gardenService.addPlantToGarden(this.$route.params.id)
        .then((response) => {
          if (response.data) {
            this.getPlants();
            this.plantAdded = true;
            this.showAddedMessage = true;
            setTimeout(() => {
              this.showAddedMessage = false;
            }, 5 * 1000);
          }
        })
        .catch((err) => {
          console.log(err);
        })
    },
    getPlants() {
      gardenService
        .getGardenByUser(this.$store.state.user.id)
        .then((response) => {
          this.garden = response.data;
          this.$store.commit("SET_GARDEN", this.garden);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkPlantNulls() {
      if (
        this.plant.default_image == null ||
        Object.keys(this.plant).length == 0
      ) {
        this.plantObject.default_image = {};
      }
    },
    formatInchValue(value) {
      return value ? `${value.value}"` : "";
    },
    formatHardinessValue(value) {
      if (typeof value === "string") {
        return value;
      } else if (
        typeof value === "object" &&
        value.min !== undefined &&
        value.max !== undefined
      ) {
        if (value.min === value.max) {
          return value.min.toString();
        } else {
          return `${value.min} - ${value.max}`;
        }
      } else {
        return "";
      }
    },
    formatOriginValue(value) {
      if (Array.isArray(value)) {
        return value.join(", ");
      } else {
        return value;
      }
    },
    formatArrayValue(value) {
      if (Array.isArray(value)) {
        return value.join(", ");
      } else {
        return value;
      }
    },
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    formatValue(value) {
      if (this.isNullOrEmpty(value)) {
        return "Information Unavailable";
      } else if (typeof value === "string") {
        return value
          .split(" ")
          .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
          .join(" ");
      } else {
        return value;
      }
    },
    removeAsterisk(value) {
      if (typeof value === "string") {
        return value.replace(/\*/g, "");
      } else {
        return value;
      }
    },
  },
};
</script>

<style scoped>
p{
  font-size: 22.5px; 
  font-weight: 300; 
  letter-spacing:.15px; 
}
.capitalize {
  text-transform: capitalize;
  margin-top: 50px;
  margin-bottom: 50px;
  font-size: 65px; 
}
.label {
  font-weight: 500; 
  letter-spacing: .75px; 
}

#plant-header {
  margin-bottom: -65px;
  margin-left: 25px; 
}

.button-wrapper-add-to-garden {
  display: flex;
  justify-content: center;
  margin-top: -40px; 
}

.add-to-my-garden-button {
  margin-top: -1px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 50px;
  padding: 10px 15px;
  font-size: 23px;
  cursor: pointer;
  transform: scale(1);
  transition: transform 0.3s ease-in-out;
}

.add-to-my-garden-button:hover {
  background-color: rgb(255, 208, 0);
  transform: scale(1.05);
}

#added-to-garden-message {
  display: flex;
  justify-content: center;
}

.plant-info{
  padding-bottom: 15px; 
}

#plant-image >img{
  width: auto;
  max-width: 30vw;
  height: auto;
  max-height: 850px; 
}

.invasive {
  color: red;
  font-weight: bold;
}
</style>