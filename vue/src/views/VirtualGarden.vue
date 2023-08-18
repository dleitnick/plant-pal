<template>
  <div id="virtual-garden">
    <h1>{{ title }}</h1>
    <virtual-garden-plants
      v-if="gardenExists"
      :garden="garden"
      :personalGarden="true"
      @update-garden="updateGarden"
    />
    <div id="care-schedule-button" v-if="gardenExists">
      <!-- && (!showCareSchedule || gardenUpdated) -->
      <button @click="getCustomCare()" :disabled="isLoadingCustomCare">
        {{
          isLoadingCustomCare
            ? "Generating..."
            : "Generate your customized care schedule"
        }}
      </button>
    </div>
    <div id="ball-container" v-if="isLoadingCustomCare">
      <div id="ball-1" class="circle"></div>
      <div id="ball-2" class="circle"></div>
      <div id="ball-3" class="circle"></div>
    </div>
    <care-schedule v-if="showCareSchedule" />
  </div>
</template>

<script>
import VirtualGardenPlants from "../components/VirtualGardenPlants.vue";
import CareSchedule from "../components/CareSchedule.vue";
import authService from "../services/AuthService.js";
import gardenService from "../services/GardenService";
// import PlantsService from "../services/PlantsService";
import recommendationService from "../services/RecommendationService.js";

export default {
  components: { VirtualGardenPlants, CareSchedule },
  data() {
    return {
      gardenOwner: {},
      title: "",
      garden: [],
      gardenCareGuide: [],
      chatMessage: {
        content: "",
        function_call: null,
        name: null,
        role: "user",
      },
      chatMessagesInput: [],
      careSchedule: [],
      isLoadingCustomCare: false,
      gardenUpdated: false,
      isButtonDisabled: false,
      gardenCareSizeChanged: true, //set true until creating the functionality to only select certain plants in garden
    };
  },
  computed: {
    showCareSchedule() {
      return (
        this.$store.state.careSchedule != null &&
        this.$store.state.careSchedule.length > 0
      );
    },
    gardenExists() {
      return this.garden && this.garden.length > 0;
    },
  },
  created() {
    this.setTitle();
    if (this.$route.params.id == this.$store.state.user.id) {
      if (this.garden && this.$store.state.garden.length > 0) {
        this.garden = this.$store.state.garden;
        this.$store.commit("SET_FILTERED_GARDEN", this.garden);
      } else {
        this.getPlants();
      }
    }
  },
  methods: {
    updateGarden() {
      console.log("updating garden");
      this.garden = this.$store.state.garden;
      this.gardenUpdated = true;
    },
    getPlants() {
      gardenService
        .getGardenByUser(this.$route.params.id)
        .then((response) => {
          this.garden = response.data;
          this.$store.commit("SET_GARDEN", this.garden);
          this.$store.commit("SET_FILTERED_GARDEN", this.garden);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getCustomCare() {
      // this.isButtonDisabled = true;
      if (
        this.$store.state.careSchedule != null &&
        this.$store.state.careSchedule.length > 0 &&
        !this.gardenUpdated &&
        !this.gardenCareSizeChanged
      ) {
        this.careSchedule = this.$store.state.careSchedule;
      } else {
        this.gardenCareGuide = [];
        this.getCareGuides();
      }
      // setTimeout(() => {
      //   this.isButtonDisabled = false;
      // }, 30000);
    },
    getCareGuides() {
      this.isLoadingCustomCare = true;
      let bundledPlantsForCareGuide = this.$store.state.filteredGarden.map((p) => {
        return {
          plantName: p.common_name,
          scientificName: p.scientific_name[0],
        };
      });
      this.getAiCareGuide(bundledPlantsForCareGuide);
    },
    getAiCareGuide(bundledPlants) {
      this.chatMessagesInput = [];
      this.chatMessage.content = JSON.stringify(bundledPlants);
      // this.chatMessage.content = JSON.stringify(this.gardenCareGuide);
      this.chatMessagesInput.push(this.chatMessage);
      recommendationService
        .getCareGuide(this.chatMessagesInput)
        .then((response) => {
          this.careSchedule = response.data;
          this.$store.commit("SET_CARE_SCHEDULE", this.careSchedule);
          this.isLoadingCustomCare = false;
          this.gardenUpdated = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setTitle() {
      authService
        .getUser(this.$route.params.id)
        .then((response) => {
          this.gardenOwner = response.data;
          if (this.gardenOwner.id === this.$store.state.user.id) {
            this.title = "Your Virtual Garden";
          } else {
            this.title = `${this.gardenOwner.username}'s Virtual Garden`;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
#virtual-garden > h1 {
  font-family: Roboto;
  font-weight: 900;
  font-size: 4rem;
  margin-top: 0px;
  margin-bottom: 0px;
}

#care-schedule-button {
  margin-top: 15px;
  display: flex;
  justify-content: center;
}

#care-schedule-button > button {
  font-family: Roboto;
  font-size: 2rem;
  cursor: pointer;
  border: 3px solid rgb(200, 196, 189);
  color: rgb(200, 196, 189);
  background-color: #181a1b;
  padding: 30px;
  border-radius: 100px;
  transition: font-size 1.5 ease-in-out; 
}

#care-schedule-button > button:hover {
  background-color: #212324;
  font-size: 2.03rem;
  border-color: forestgreen; 
  color: white; 
}


.circle {
  display: inline-block;
  background-color: rgb(255, 255, 255);
  height: 30px;
  width: 30px;
  border-radius: 25px;
}

#ball-container {
  display: flex;
  justify-content: center;
  height: 50px;
  margin-top: 50px;
}
   
/* bouncing balls loading */
#ball-1 {
  -webkit-animation-name: bounce;
  animation-name: bounce;
  -webkit-animation-delay: 1s;
  animation-delay: 1s;
  -webkit-animation-duration: 1.2s;
  animation-duration: 1.2s;
  -webkit-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
}

#ball-2 {
  -webkit-animation-name: bounce;
  -webkit-animation-delay: 1.1s;
  -webkit-animation-duration: 1.2s;
  -webkit-animation-iteration-count: infinite;
  animation-name: bounce;
  animation-delay: 1.1s;
  animation-duration: 1.2s;
  animation-iteration-count: infinite;
}

#ball-3 {
  -webkit-animation-name: bounce;
  -webkit-animation-delay: 1.2s;
  -webkit-animation-duration: 1.2s;
  -webkit-animation-iteration-count: infinite;
  animation-name: bounce;
  animation-delay: 1.2s;
  animation-duration: 1.2s;
  animation-iteration-count: infinite;
}

@-webkit-keyframes bounce {
  0% {
    transform: translateY(0);
  }

  40% {
    transform: translateY(23px);
  }

  60% {
    transform: translateY(-25px);
  }

  80% {
    transform: translateY(0);
  }
}

@keyframes bounce {
  0% {
    transform: translateY(0);
  }

  40% {
    transform: translateY(23px);
  }

  60% {
    transform: translateY(-25px);
  }

  80% {
    transform: translateY(0);
  }
}
</style>
