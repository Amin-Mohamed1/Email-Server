import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'animate.css/animate.min.css';
import '@fortawesome/fontawesome-free/css/all.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';


library.add(faSearch);
createApp(App).use(router).mount('#app')
app.component('font-awesome-icon', FontAwesomeIcon);
