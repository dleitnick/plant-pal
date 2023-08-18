<template>
  <div id="plant-search">
      <form id="plant-search-form" @submit.prevent="search">
        <input type="text" v-model="plantName" :placeholder="randomSearchExample()"/>
        <span class="material-symbols-outlined" @click.prevent="search"> search </span>
        <input type="submit" value="Search" style="display:none;" />
      </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      plantName: "",
      searchExamples: [
        "Sunflower", "Daisy", "Rose", "Tulip",
        "Orchid", "Lily", "Cactus", "Bamboo",
        "Lavender", "Mint", "Sage", "Thyme",
        "Fern", "Ivy", "Sequoia", "Cherry Blossom",
        "Magnolia", "Azalea", "Hibiscus", "Jasmine",
        "Blackberry", "Palm", "Oak", "Maple",
        "Pine", "Cedar", "Olive", "Birch",
        "Spruce", "Willow", "Apple", "Lemon"
      ]
    };
  },
  methods: {
    search() {
      this.$router.push({ path: "/plants", query: { search: this.plantName } });
      this.plantName = "";
    },
    randomSearchExample() {
      return "Plant ex: " + this.searchExamples[Math.floor(Math.random() * this.searchExamples.length)];
    }
  },
};
</script>

<style>
#plant-search-form {
  width: fit-content;
  border-radius: 28px;
  background: #2b2930;
  height: 40px;
  display: flex;
  align-items: center;
}

#plant-search-form>input {
  padding: 8px;
  padding-left: 15px;
  color: white;
  background: none;
  border-radius: 28px;
  border: none;
  font-size: 1.1rem;
}

#plant-search-form>input:focus {
  outline: none;
  border: none;
  /* Or you can specify the border style you want here */
}

#plant-search-form>.material-symbols-outlined {
  cursor: pointer;
  color: white;
  font-variation-settings: "FILL" 0, "wght" 400, "GRAD" 0, "opsz" 48;
  padding-right: 15px;
}

#header-bottom>#plant-search>#plant-search-form {
  transition: box-shadow 0.15s ease-in-out;
}

#header-bottom>#plant-search>#plant-search-form:hover {
  box-shadow: 0 0 15px 1px rgb(98,147,88);
}
</style>