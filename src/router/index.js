import { createRouter, createWebHistory } from 'vue-router';
import LoginComponent from '@/components/LoginComponent.vue';
import SignUpComponent from '@/components/SignUpComponent.vue';
import HomeComponent from '@/components/HomeComponent.vue';
import ProfileComponentVue from '@/components/ProfileComponent.vue';
import InboxComponentVue from '@/components/InboxComponent.vue';
import ComposeComponentVue from '@/components/ComposeComponent.vue';
import ContactComponentVue from '@/components/ContactComponent.vue';
import SentComponentVue from '@/components/SentComponent.vue';


const routes = [
  { path: '/home', component: HomeComponent },
  { path: '/login', component: LoginComponent },
  { path: '/signup', component: SignUpComponent },
  { path: '/profile', component: ProfileComponentVue, children: [
    { path: 'inbox', components: { default: InboxComponentVue } },
    { path: 'compose', components: { default: ComposeComponentVue } },
    { path: 'contact', components: { default: ContactComponentVue } },
    {path : 'sent' , components: {default: SentComponentVue  }}
  ]}
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
