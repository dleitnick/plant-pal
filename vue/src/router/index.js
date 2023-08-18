import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import PlantDetailsView from '../views/PlantDetailsView.vue'
import PlantSearchResultsView from '../views/PlantSearchResultsView.vue'
import Recommendation from '../views/Recommendation.vue'
import plantsService from '../services/PlantsService.js'
import VirtualGarden from '../views/VirtualGarden.vue'
import AuthView from '../views/AuthView.vue';
import Loading from '../views/Loading.vue';
import Forums from '../views/Forums.vue';
import ForumSpecific from '../views/ForumSpecific.vue';
import ForumPost from '../views/ForumPost.vue';
import AboutSectionView from '../views/AboutSectionView.vue';
import UserProfile from '../views/UserProfile.vue'

const defaultTitle = 'Plant Pal';

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        title: `Login | ${defaultTitle}`,
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        title: `Register | ${defaultTitle}`,
        requiresAuth: false
      }
    },
    {
      path: "/plants/:id",
      name: "plantDetails",
      component: PlantDetailsView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/plants",
      name: "plants",
      component: PlantSearchResultsView,
      meta: {
        title: `Plant Search | ${defaultTitle}`,
        requiresAuth: false
      }
    },
    {
      path: "/recommendations",
      name: "recommendations",
      component: Recommendation,
      meta: {
        title: `Garden Guru | ${defaultTitle}`,
        requiresAuth: false
      }
    },
    {
      path: "/users/:id/garden",
      name: "virtualGarden",
      component: VirtualGarden,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/auth-view",
      name: "authView",
      component: AuthView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/loading",
      name: "loading",
      component: Loading,
      meta: {
        title: 'Loading | Plant Pal',
        requiresAuth: false
      }
    },
    {
      path: "/forums",
      name: "forums",
      component: Forums,
      meta: {
        title: 'Forums | Plant Pal',
        requiresAuth: true
      }
    },
    {
      path: "/forums/:name",
      name: "forumSpecific",
      component: ForumSpecific,
      meta: {
        title: 'Forums | Plant Pal',
        requiresAuth: true
      }
    },
    {
      path: "/forums/:name/:postTitle",
      name: "forumPost",
      component: ForumPost,
      meta: {
        title: 'Forums | Plant Pal',
        requiresAuth: true
      }
    },
    {
      path: "/about",
      name: "about",
      component: AboutSectionView,
      meta: {
        title: `About Us | ${defaultTitle}`,
        requiresAuth: false
      },
    },
    {
      path: "/users/:username", 
      name: "userProfile",
      component: UserProfile, 
      meta: {
        requiresAuth: true
      }
    }
  ],
  scrollBehavior(to, from, savedPosition) {
    // If there's a saved position (i.e., we're navigating back), return that position
    if (savedPosition) {
      return savedPosition;
    }

    // If a hash is provided (e.g., #section), scroll to that element
    if (to.hash) {
      return { selector: to.hash };
    }

    // Otherwise, scroll to the top of the page
    return { x: 0, y: 0 };
  }
})

router.beforeEach((to, from, next) => {
  setPlantTitle(to);
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next({ name: 'authView' });
  } else {
    // Else let them go to their next destination
    next();
  }
});

function setPlantTitle(to) {
  if (to.name === 'plantDetails') {
    getPlantName(to.params.id)
      .then((plantName) => {
        document.title = ` ${titleCase(plantName)} | ${defaultTitle}`;
      })

  } else {
    document.title = to.meta.title || defaultTitle;
  }
}

// TODO: Get userByID using a userService to fill in the name of the User
// function setGardenTitle(to) {
//   if (to.name === 'virtualGarden') {
//     document.title = ` ${store.state.user.username}} | ${defaultTitle}`;
//   } else {
//     document.title = to.meta.title || defaultTitle;
//   }
// }

async function getPlantName(id) {
  try {
    const response = await plantsService
      .getPlantDetails(id)
    return response.data.common_name
  } catch (err) {
    console.log(err)
    return ''
  }
}

function titleCase(title) {
  return title
    .split(' ')
    .map((str) => {
      return str.charAt(0).toUpperCase() + str.slice(1);
    })
    .join(' ');
}

export default router;
