<template>
  <div class="plant-card" @click="goToDetails">
    <img v-if="!nullImage" :src="plant.default_image.small_url" alt="" />
    <img class="placeholder-image" v-else :src="placeholderImage" alt="" />
    <div class="plant-card-content">
      <h3>{{ plant.common_name }}</h3>
      <h4>{{ plant.scientific_name[0] }}</h4>
      <p>{{ reduceTextLength(plant.description, 300) }}</p>
    </div>
    <div id="remove-button-wrapper" v-if="personalUserGarden">
      <button
        title="Remove"
        id="remove-from-garden-button"
        @click.stop="removeFromGarden()"
      >
        <span class="material-symbols-outlined"> delete </span>
      </button>
    </div>
    <div class="checkbox-wrapper-7" v-if="personalUserGarden">
      <input
        class="tgl tgl-ios"
        :id="`cb2-${plant.id}`"
        type="checkbox"
        v-model="checkboxValue"
        @click.stop
        @change="toggleSelection"
      />
      <label class="tgl-btn" :for="`cb2-${plant.id}`" @click.stop></label>
    </div>
  </div>
</template>

<script>
export default {
  name: "plant-card",
  props: {
    plant: Object,
    personalUserGarden: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      placeholderImage: "",
      checkboxValue: true,
      nullImage:
        this.plant.default_image === null ||
        this.plant.default_image.small_url === null,
    };
  },
  created() {
    this.replaceNullImage();
  },
  methods: {
    reduceTextLength(text, length) {
      if (text.length <= length) {
        return text;
      }
      let subText = text.substr(0, length);
      let lastPeriodIndex = subText.lastIndexOf(".");
      if (lastPeriodIndex === -1) {
        return subText;
      }
      return subText.substr(0, lastPeriodIndex + 1);
    },
    goToDetails() {
      console.log("Navigating away");
      console.log(this.plant.id);
      this.$router.push({
        name: "plantDetails",
        params: { id: this.plant.id },
      });
    },
    replaceNullImage() {
      if (this.nullImage) {
        this.placeholderImage =
          "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg";
      }
    },
    removeFromGarden() {
      console.log("emitting remove-plant");
      this.$emit("remove-plant", this.plant.id);
    },
    toggleSelection() {
      let filteredGarden = [...this.$store.state.filteredGarden];
      if (this.checkboxValue) {
        filteredGarden.push(this.plant);
      } else {
        filteredGarden = filteredGarden.filter(p => p.id !== this.plant.id);
      }
      this.$store.commit("SET_FILTERED_GARDEN", filteredGarden);
      //emit to parent that it should be added to array
      console.log("Toggle selection here");
    },
  },
};
</script>

<style>
.plant-card {
  position: relative;
  width: 300px;
  color: rgb(200, 196, 189);
  background-color: #181a1b;
  cursor: pointer;
}

.plant-card > img {
  width: 300px;
  height: 200px;
  object-fit: cover;
  margin: 0;
}

.plant-card > .placeholder-image {
  height: 200px;
}

.plant-card-content {
  margin: 7px;
}

.plant-card-content > h3 {
  font-family: Roboto;
  font-size: 1.2rem;
  font-weight: 500;
  margin: 0 0 0 0;
  padding-left: 4px;
  text-transform: capitalize;
}

.plant-card-content > h4 {
  font-family: Roboto;
  font-size: 1rem;
  font-weight: 300;
  font-style: italic;
  margin: 0 0 0 0;
  padding: 4px;
}

.plant-card-content > p {
  font-family: Roboto;
  font-size: 0.8rem;
  font-weight: 300;
  margin: 0 0 0 0;
  padding: 4px 4px 8px 4px;
}

#remove-button-wrapper {
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  justify-content: flex-end;
}

#remove-button-wrapper > button {
  cursor: pointer;
  border: 2px solid rgb(200, 196, 189);
  color: rgb(200, 196, 189);
  background-color: #181a1b;
  padding: 10px;
  border-radius: 30px;
  transition: transform 1.0 ease-in-out; 
}

#remove-button-wrapper > button:hover {
  background-color: #ffffff;
  transform: scale(1.25);
}

#remove-button-wrapper > button:focus {
  border-color: forestgreen;
}

#remove-button-wrapper > button > .material-symbols-outlined {
  font-variation-settings: "FILL" 0, "wght" 400, "GRAD" 0, "opsz" 48;
}

.checkbox-wrapper-7 {
  position: absolute;
  bottom: 10px;
  right: 10px;
}

.checkbox-wrapper-7 .tgl {
  display: none;
}

.checkbox-wrapper-7 .tgl,
.checkbox-wrapper-7 .tgl:after,
.checkbox-wrapper-7 .tgl:before,
.checkbox-wrapper-7 .tgl *,
.checkbox-wrapper-7 .tgl *:after,
.checkbox-wrapper-7 .tgl *:before,
.checkbox-wrapper-7 .tgl + .tgl-btn {
  box-sizing: border-box;
}

.checkbox-wrapper-7 .tgl::-moz-selection,
.checkbox-wrapper-7 .tgl:after::-moz-selection,
.checkbox-wrapper-7 .tgl:before::-moz-selection,
.checkbox-wrapper-7 .tgl *::-moz-selection,
.checkbox-wrapper-7 .tgl *:after::-moz-selection,
.checkbox-wrapper-7 .tgl *:before::-moz-selection,
.checkbox-wrapper-7 .tgl + .tgl-btn::-moz-selection,
.checkbox-wrapper-7 .tgl::selection,
.checkbox-wrapper-7 .tgl:after::selection,
.checkbox-wrapper-7 .tgl:before::selection,
.checkbox-wrapper-7 .tgl *::selection,
.checkbox-wrapper-7 .tgl *:after::selection,
.checkbox-wrapper-7 .tgl *:before::selection,
.checkbox-wrapper-7 .tgl + .tgl-btn::selection {
  background: none;
}

.checkbox-wrapper-7 .tgl + .tgl-btn {
  outline: 0;
  display: block;
  width: 2em;
  height: 1em;
  position: relative;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.checkbox-wrapper-7 .tgl + .tgl-btn:after,
.checkbox-wrapper-7 .tgl + .tgl-btn:before {
  position: relative;
  display: block;
  content: "";
  width: 50%;
  height: 100%;
}

.checkbox-wrapper-7 .tgl + .tgl-btn:after {
  left: 0;
}

.checkbox-wrapper-7 .tgl + .tgl-btn:before {
  display: none;
}

.checkbox-wrapper-7 .tgl:checked + .tgl-btn:after {
  left: 50%;
}

.checkbox-wrapper-7 .tgl-ios + .tgl-btn {
  background: #181a1b;
  border-radius: 2em;
  padding: 2px;
  transition: all 0.4s ease;
  border: 1px solid #e8eae9;
}

.checkbox-wrapper-7 .tgl-ios + .tgl-btn:after {
  border-radius: 2em;
  background: #e8eae9;
  transition: left 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275),
    padding 0.3s ease, margin 0.3s ease;
  box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.1), 0 4px 0 rgba(0, 0, 0, 0.08);
}

.checkbox-wrapper-7 .tgl-ios + .tgl-btn:hover:after {
  will-change: padding;
}

.checkbox-wrapper-7 .tgl-ios + .tgl-btn:active {
  box-shadow: inset 0 0 0 2em #e8eae9;
}

.checkbox-wrapper-7 .tgl-ios + .tgl-btn:active:after {
  padding-right: 0.8em;
}

.checkbox-wrapper-7 .tgl-ios:checked + .tgl-btn {
  background: forestgreen;
}

.checkbox-wrapper-7 .tgl-ios:checked + .tgl-btn:active {
  box-shadow: none;
}

.checkbox-wrapper-7 .tgl-ios:checked + .tgl-btn:active:after {
  margin-left: -0.8em;
}
</style>