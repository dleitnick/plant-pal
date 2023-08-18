<template>
  <div id="loading-plant">
    <lottie :options="defaultOptions" :height="400" :width="400" v-on:animCreated="handleAnimation"/>
  </div>
</template>

<script>
  import Lottie from 'vue-lottie';
  import * as animationData from '../../assets/growing-plant.json';

  export default {
    name: 'app',
    components: {
      'lottie': Lottie
    },
    data() {
      return {
        defaultOptions: {animationData: animationData},
        animationSpeed: 1
      }
    },
    methods: {
      handleAnimation: function (anim) {
        this.anim = anim;
      },
    }
  }
</script>

<style>

</style>