<template>
  <div class="about-section">
    <div class="about-header">
      <h2>About</h2> 
      <p>{{ originDescription }}</p>
    </div>
  <h3 class="team-header">Meet the Team</h3>
    <div class="team-members">
      <a v-for="(member, index) in teamMembers" :key="index" :href="member.linkedin" target="_blank" rel="noopener noreferrer" class="member-card">
        <div class="member-image" @mouseover="showDetails(index)" @mouseleave="hideDetails(index)">
          <img :src="member.imageSrc" alt="Team Member" />
          <div v-if="member.showDetails" class="member-details">
            <p>{{ member.description }}</p>
            <a :href="member.linkedin" target="_blank" rel="noopener noreferrer"></a>
          </div>
        </div>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      originDescription: "Meet PlantPal: the digital friend every plant enthusiast needs. With an expansive plant database, expert care guides, and the ability to curate your virtual garden, PlantPal empowers you to create thriving indoor and outdoor spaces. Our community forums foster connections and learning, while the Garden Guru ensures you're never far from reliable gardening advice. Join PlantPal today and embark on a journey of growth and greenery!"
,
      teamMembers: [
        {
          imageSrc: require('../assets/imgs/joey.png'),
          description: "Joey Pagnanelli",
          linkedin: "https://www.linkedin.com/in/jpagnanelli/",
          showDetails: false // Added this line
        },
        {
          imageSrc: require('../assets/imgs/daniel.png'),
          description: "Daniel Leitnick",
          linkedin: "https://www.linkedin.com/in/daniel-leitnick/",
          showDetails: false // Added this line
        },
        {
          imageSrc: require('../assets/imgs/ryan.png'),
          description: "Ryan Blakenship",
          linkedin: "https://www.linkedin.com/in/ryanmblankenship/",
          showDetails: false // Added this line
        }
      ]
    };
  },
  methods: {
    showDetails(index) {
      this.teamMembers[index].showDetails = true;
    },
    hideDetails(index) {
      this.teamMembers[index].showDetails = false;
    }
  }
};
</script>


<style scoped>
.about-section {
  padding: 20px;
  border-radius: 10px;
 
}

.about-header {
  text-align: center;
  margin-bottom: 20px;
}

.about-header h2 {
  color: white; 
  font-size: 4.5rem;
  margin-top: 20px;
}

.about-header p {
  color: white; 
  font-size: 30px;
  margin-top: -30px; 
  margin-bottom: -70px; 
}

h3{
  display: flex; 
  justify-content: center; 
  font-size: 2.5rem; 
  margin-top: 115px; 
}
.team-members {
  display: flex;
  justify-content: space-evenly;
  align-items: flex-start;
  flex-wrap: wrap;
  margin-top: 10px;
}

.member-card {
  width: 18%;
  margin: 15px 0;
  padding: 10px;
  transition: box-shadow 0.15s ease-in-out;
  border-radius: 999px; 
  position: relative;
}
.member-card:hover{
box-shadow: 0 0 4px 7px white;
border-radius: 999px; 
}

.member-card::after {
  content: "LinkedIn"; /* Text content of the label */
  position: absolute; /* Position the label absolutely */
  bottom: -77px; /* Adjust the position as needed */
  left: 50%; /* Position it at the center */
  transform: translateX(-50%); /* Center it horizontally */
  background-color: rgb(31, 46, 36); /* Background color of the label */
  color: white; /* Text color of the label */
  padding: 5px 10px; /* Padding for the label */
  border-radius: 45px; /* Rounded corners */
  opacity: 0; /* Hide the label initially */
  transition: opacity 0.3s ease-in-out; /* Add a transition effect */
  font-size: 20px; 
}

.member-card:hover::after {
  opacity: 1; /* Show the label on hover */
}

.member-image {
  position: relative;
  cursor: pointer;
}

.member-image img {
  width: 100%;
  border-radius: 999px;  
}

.member-details {
  position: absolute;
  color: white;
  margin-bottom: -95px; 
  padding: 10px;
  width: 100%;
  bottom: 0;
  left: 0;
  opacity: 0;
  border-radius: 999px; 
  transition: opacity 0.3s;
  display: flex;
  justify-content: center; 
  text-align: center; 
}

.member-image:hover .member-details {
  opacity: 1;
}

.member-details > p{
 font-size: 30px; 
 letter-spacing: 1px; 
}
</style>
