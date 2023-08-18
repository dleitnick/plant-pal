<template>
    <div id="ball-container">
        <div id="ball-1" class="circle"></div>
        <div id="ball-2" class="circle"></div>
        <div id="ball-3" class="circle"></div>
    </div>
</template>

<script>
export default {
    name: 'loading-balls',
    props: {
        color: {
            type: String,
            default: 'rgb(255, 255, 255)'
        },
        ballSize: {
            type: String,
            default: '30px'
        },
        gap: {
            type: String,
            default: '10px'
        }
    },
    created() {
        document.documentElement.style.setProperty('--my-color', this.color);
        document.documentElement.style.setProperty('--ball-size', this.ballSize);
        document.documentElement.style.setProperty('--ball-gap', this.gap);
    }

}
</script>

<style>
:root {
    --my-color: white;
    --ball-size: 30px;
    --ball-gap: 10px;
}

.circle {
    display: inline-block;
    background-color: var(--my-color) !important;
    height: var(--ball-size);
    width: var(--ball-size);
    border-radius: 25px;
    box-shadow: 5px 5px 5px rgba(0, 0, 0, 1);
}

#ball-container {
    display: flex;
    justify-content: center;
    gap: var(--ball-gap);
    height: 50px;
    margin-top: 50px;
}

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