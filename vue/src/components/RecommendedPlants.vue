<template>
    <div id="recommended-plants">
        <h2 v-if="recommendedPlants.length > 0">Garden Guru's Plant Recommendations</h2>
        <div id="recommended-plants-container">
            <plant-card v-for="plant in recommendedPlants" :key="plant.id" :plant="plant" />
        </div>
    </div>
</template>

<script>
import PlantCard from "./PlantCard.vue"
import recommendationService from "../services/RecommendationService.js";
import plantsService from "../services/PlantsService.js";

export default {
    name: 'recommended-plants',
    components: { PlantCard },
    data() {
        return {
            recommendedPlants: this.$store.state.recommendedPlants,
            testData: "Ah, it's wonderful to hear that your sister-in-law already has a passion for indoor plants! Here are some good indoor plant options that could make great additions to her collection: 1. Snake Plant (Sansevieria): Snake plants are known for being low-maintenance and adaptable to various light conditions. They have beautiful upright foliage and can help purify the air by removing toxins. 2. Pothos (Epipremnum aureum): Pothos plants are popular choices for indoor gardens due to their trailing vines and heart-shaped leaves. They thrive in bright, indirect light and are relatively tolerant of different watering schedules. 3. Peace Lily (Spathiphyllum): Peace lilies are elegant plants with glossy, dark green leaves and beautiful white flowers. They can tolerate low light but prefer bright, indirect light. These plants are great for improving indoor air quality. For indoor lighting, it's typically best to place these plants near a window with bright, indirect light. If natural light is limited, consider using grow lights to supplement their lighting needs. Remember to consider her specific preferences and needs when choosing the plants. I hope these suggestions help you find the perfect indoor plants for your sister-in-law. Enjoy spreading the love of plants! If you have any further questions, feel free to ask.",
            recommendedPlantsResponse: [],
        }
    },
    created() {
        // this.getRecommendedPlants();
    },
    computed: {
        shouldFetchRecommendedPlants() {
            return this.$store.state.fetchRecommendedPlants;
        },
        lastChatMessage() {
            if (this.$store.state.chatMessages.length > 0) {
                return this.$store.state.chatMessages[this.$store.state.chatMessages.length - 1].content;
            }
            else return '';
        }
    },
    methods: {
        async getRecommendedPlants() {
            try {
                const response = await recommendationService.getRecommendedPlants(this.lastChatMessage);
                console.log(response.data[2].content);
                this.recommendedPlantsResponse = JSON.parse(response.data[2].content);
                await this.getPlants();
            } catch (err) {
                console.log(err);
            }
        },
        async getPlants() {
            try {
                const plantPromises = this.recommendedPlantsResponse.map((plant) => {
                    return plantsService.getPlantDetails(plant.id);
                });

                const plantDetails = await Promise.all(plantPromises);

                this.recommendedPlants = plantDetails.map((response) => response.data);
                this.$store.commit("SET_RECOMMENDED_PLANTS", this.recommendedPlants);
            } catch (err) {
                console.log(err);
            }
        }
    },
    watch: {
        shouldFetchRecommendedPlants(value) {
            if (value) {
                console.log("Fetching Garden Guru's recommendations...")
                this.getRecommendedPlants();
                this.$store.commit("SET_FETCH_RECOMMENDED_PLANTS", false);
            }
        }
    }

}
</script>

<style>
#recommended-plants-container {
    display: flex;
    justify-content: center;
    gap: 10px; 
}

#recommended-plants > h2 {
    font-family: 'Roboto Condensed';
}
</style>