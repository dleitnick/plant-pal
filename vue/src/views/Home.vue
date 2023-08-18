<template>
  <div class="home">
    <div id="tagline">
      <h1>{{ randomTagline() }}</h1>
    </div>
    <div id="home-search">
      <plant-search />
    </div>
    <div id="search-explanation">
      <h2>Delve into our comprehensive database to discover detailed information about a wide variety of plants.</h2>
    </div>
  </div>
</template>

<script>
import PlantSearch from '../components/PlantSearch.vue';

export default {
  name: "home",
  components: {
    PlantSearch,
  },
  data() {
    return {
      taglines: [
        "Cultivate your green haven: Sow, Share, Succeed!",
        "Find your plant soul-mate: Guidance for every green thumb!",
        "Unearth the secrets of plant care: From seedlings to blooms!",
        "Build your virtual eden: Plant knowledge at your fingertips!",
        "Nourish, flourish, cherish: Your guide to plant parenthood!",
        "Plant your dreams: Grow a garden of possibilities!",
        "From novice to master gardener: Tools for every journey!",
        "Sprout new ideas: Discover the world of plants today!",
        "Rooted in wisdom: Plant care for the modern age!",
        "A garden of knowledge: Explore, experiment, enjoy!"
      ]
    }
  },
  methods: {
    randomTagline() {
      return this.taglines[Math.floor(Math.random() * this.taglines.length)];
    }
  }
};
</script>

<style>
#tagline {
  max-width: 40%;
  margin: auto;
}

#tagline>h1 {
  font-family: 'Roboto Condensed';
  font-weight: 900;
  font-size: 5.5rem;
  text-align: center;
}

#home-search>#plant-search {
  margin: auto;
}

#home-search {
  display: flex;
  justify-content: center;
  margin: auto;
}

#home-search>#plant-search>#plant-search-form {
  width: fit-content;
  border-radius: 84px;
  background: #2b2930;
  height: 120px;
  display: flex;
  align-items: center;
  transition: box-shadow 0.15s ease-in-out;
}

#home-search>#plant-search>#plant-search-form:hover {
  box-shadow: 0 0 15px 5px rgb(98,147,88);
}

#home-search>#plant-search>#plant-search-form>input {
  padding: 16px;
  padding-left: 45px;
  color: white;
  background: none;
  border-radius: 84px;
  border: none;
  font-size: 3.3rem;
}

#home-search>#plant-search>#plant-search-form>input:focus {
  outline: none;
  border: none;
}

#home-search>#plant-search>#plant-search-form>.material-symbols-outlined {
  cursor: pointer;
  color: white;
  font-size: 5rem;
  font-variation-settings: "FILL" 0, "wght" 400, "GRAD" 0, "opsz" 48;
  padding-right: 30px;
}

#search-explanation>h2{
  font-family: 'roboto';
  text-align: center;
  font-size: 35px;
}

@media only screen and (max-width: 768px) {
  
}
</style>