<template>
  <div id="no-results">
    <h3>No results found for {{searchWord}}!</h3>
    <p>{{randomQuip()}}</p>
  </div>
</template>

<script>
export default {
  name: "no-results",
  props: {
    searchWord: String,
  },
  data() {
    return {
      responseQuips: [
        "Looks like our search results are as scarce as a desert flower in the rain.",
        "Oops! Our search is leaf-ing us empty-handed, like a tree in winter.",
        "Seems like our plant knowledge got rooted out! No results found.",
        "Sorry, it's like trying to find a needle in a haystack made of bamboo shoots.",
        "Our search results are currently taking a botanical vacation. Please try again later!",
        "Looks like our plant database is stuck in a photosynthesis nap.",
        "Our search results seem to be hiding among the plants, playing a game of hide and seek.",
        "Alas, our search came up tulip-less. Time to dig deeper!",
        "We apologize for the bare search results. It's like a garden after a plant sale!",
        "Looks like our search algorithm went on strike, taking a leaf of absence.",
        "Ever searched for a plant in a forest? That's how our results feel right now!",
        "Our search results got lost in the jungle of information. Be-leaf us, we're working on it!",
        "Our plant puns are flourishing, but the search results seem to be taking a leaf from the shy violets.",
        "You've stumbled upon our secret garden - where search results play hide-and-seek!",
        "No results found, but don't be frond, we're still growing!",
        "This is awkward... It's like finding a cactus in the ocean, no search results in sight!",
        "Our search results got caught in a photosynthesis glitch. They'll be back, sprout of this world!",
        "Plant enthusiasts, don't get succulent about it! Our search results will be back soon.",
        "Searching for results on rare plants is like finding a four-leaf clover - elusive!",
        "Apologies for the fern-tastic failure! We're busy pruning the search algorithms for better results.",
      ],
    };
  },
  methods: {
    randomQuip() {
        const rando = Math.floor(Math.random() * 20)
        return this.responseQuips[rando];
    }
  }
};
</script>

<style>
</style>