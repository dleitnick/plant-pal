<template>
  <div id="search-results">
    <h2 v-if="displayResults || (searchMade && searchResults.length === 0)">Search Results</h2>
    <h2 v-else>Searching...</h2>
    <loading-plant v-if="!displayResults && !(searchMade && searchResults.length === 0)"/>
    <div v-show="displayResults" id="search-results-container">
      <search-result
        v-for="element in searchResults"
        :key="element.id"
        :plant="element"
        class="search-result-card"
      />
    </div>
    <no-results-found
      :searchWord="this.$route.query.search"
      v-if="searchMade && searchResults.length === 0"
    />
  </div>
</template>

<script>
import plantsService from "../services/PlantsService.js";
import SearchResult from "./SearchResult.vue";
import LoadingPlant from "./loaders/LoadingPlant.vue";
import NoResultsFound from "./NoResultsFound.vue";

export default {
  name: "plant-search-results",
  components: {
    SearchResult,
    NoResultsFound,
    LoadingPlant,
  },
  data() {
    return {
      searchResults: [],
      searchMade: false,
      isLoading: false,
      displayResults: false,
    };
  },
  computed: {
    showSearchResults() {
      return this.searchResults.length > 0;
    }
  },
  created() {
    this.searchMade = false;
    this.getSearchResults();
  },
  watch: {
    $route(to, from) {
      if (to.query.search !== from.query.search) {
        this.searchMade = false;
        this.searchResults = [];
        this.displayResults = false;
        this.getSearchResults();
      }
    },
    showSearchResults(isTrue) {
      if (isTrue) {
        setTimeout(() => {
          this.displayResults = true;
        }, 2700);
      }
    }
  },
  methods: {
    getSearchResults() {
      console.log("searching...");
      console.log(this.$route.query.search);
      const search = this.$route.query.search;
      if (search) {
        this.isLoading = true;
        plantsService
          .findPlantsByName(this.$route.query.search)
          .then((response) => {
            this.searchResults = response.data.data;
            this.searchMade = true;
            console.log(this.searchResults);
          })
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
              this.isLoading = false;
          });
      }
    },
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Caprasimo&family=Cormorant+Garamond:wght@300;600;700&family=Roboto:wght@100;300;400;500;700;900&display=swap');

.search-result-card {
  border: 2px solid rgb(0, 0, 0);
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
  margin: 20px;
  cursor: pointer;
}

.search-result-card:hover {
  transform: translateY(-5px);
}

h2 {
  font-family: 'Caprasimo', cursive;
  font-weight: 10;
  font-size: 50px;
  text-align: center;
  justify-content: center;
  padding-top: 20px; 
}

#search-results-container {
  display: flex;
  flex-wrap: wrap;
  width: 100vw;
  max-width: 100%;
  justify-content: center;
}
</style>