<template>
    <div class= "care-schedule-wrapper">
    <div id="care-schedule" v-if="careSchedule.length > 0">
        <div v-html="markdownToHtml"></div>
    </div>
    </div>
</template>

<script>
import { marked } from 'marked';

export default {
    name: 'care-schedule',
    data() {
        return {

        }
    },
    computed: {
        careSchedule() {
            return this.$store.state.careSchedule;
        },
        markdownToHtml() {
            if (this.careSchedule.length > 0) {
                return marked(this.careSchedule[this.careSchedule.length - 1].content);
            } else return '';
        }
    }

}

</script>

<style>

#care-schedule {
    font-family: Roboto;
    padding: 20px; 
    margin: 20px; 
    border-radius: 20px; 
    background-color: #181a1bb6;
    max-width: 800px; 
}

.care-schedule-wrapper{
    display: flex; 
    justify-content: center; 
}

#care-schedule > div {
    max-width: 800px;
}

#care-schedule > div > h3 {
    font-family: Roboto;
    font-weight: bold;
}

#care-schedule > div > ul > li {
    line-height: 1.5;
    font-weight: 300;
}

</style>