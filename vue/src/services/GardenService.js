import axios from 'axios';

export default {

  getGardenByUser(userId) {
    return axios.get(`/gardens/${userId}`);
  },

  addPlantToGarden(plantId) {
    return axios.post('/gardens', plantId, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
  },

  removePlantFromGarden(plantId){
    return axios.delete(`/gardens/${plantId}`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
  },

}