import { createRouter, createWebHistory } from 'vue-router';
import LoginComponent from '@/components/LoginComponent.vue';
import SignUpComponent from '@/components/SignUpComponent.vue';
import HomeComponent from '@/components/HomeComponent.vue';
import ProfileComponentVue from '@/components/ProfileComponent.vue';
import InboxComponentVue from '@/components/InboxComponent.vue';
import ComposeComponentVue from '@/components/ComposeComponent.vue';
<<<<<<< Updated upstream
import ContactComponentVue from '@/components/ContactComponent.vue';
import SentComponentVue from '@/components/SentComponent.vue';

=======
import TrashComponent from '@/components/TrashComponent.vue';
import FoldersComponent from '@/components/FoldersComponent.vue'
>>>>>>> Stashed changes

const routes = [
  { path: '/home', component: HomeComponent },
  { path: '/login', component: LoginComponent },
  { path: '/signup', component: SignUpComponent },
  { path: '/profile', component: ProfileComponentVue, children: [
    { path: 'inbox', components: { default: InboxComponentVue } },
    { path: 'compose', components: { default: ComposeComponentVue } },
<<<<<<< Updated upstream
    { path: 'contact', components: { default: ContactComponentVue } },
    {path : 'sent' , components: {default: SentComponentVue  }}
  ]}
=======
    { path: 'trash', components: { default: TrashComponent } },
    { path: 'folders', components: { default: FoldersComponent } },
  ]
  },
>>>>>>> Stashed changes
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
