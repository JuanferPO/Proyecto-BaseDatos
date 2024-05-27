import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import EspecialidadPage from '../views/EspecialidadPage.vue'
import DepartamentoPage from '../views/DepartamentoPage.vue'
import ProcedimientoPage from '../views/ProcedimientoPage.vue'
import EspecialistaPage from '../views/EspecialistaPage.vue'
import ConsultorioPage from '../views/ConsultorioPage.vue'
import CitaPage from '../views/CitaPage.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/cita'
  },
  {
    path: '/especialidad',
    name: 'Especialidad',
    component: EspecialidadPage
  },
  {
    path: '/departamento',
    name: 'Departamento',
    component: DepartamentoPage
  },
  {
    path: '/procedimiento',
    name: 'Procedimiento',
    component: ProcedimientoPage
  },
  {
    path: '/especialista',
    name: 'Especialista',
    component: EspecialistaPage
  },
  {
    path: '/consultorio',
    name: 'Consultorio',
    component: ConsultorioPage
  },
  {
    path: '/cita',
    name: 'Cita',
    component: CitaPage
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
