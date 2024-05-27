<template>
  <ion-page>
    <ion-header :translucent="true">
      <ion-toolbar>
        <ion-title>Especialidades</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <ion-modal :is-open="modalIsOpen" @didDismiss="modalIsOpen = false" :css-class="['my-custom-modal']">
        <div id="modal-content">
          <div id="data-form">
            <InputComponent v-model="nombre" id="nombre" name="nombre" label="Nombre: " />
            <ion-select v-model="especialidad" id="especialidad" name="especialidad" label="Especialidad: ">
              <ion-select-option v-for="especialidad in especialidades" :key="especialidad.id"
                :value="especialidad.id">{{ especialidad.nombre }}</ion-select-option>
            </ion-select>
            <ion-label>Hora de inicio del horario</ion-label>
            <ion-datetime v-model="horarioInicio" id="horarioInicio" name="horarioInicio"
              presentation="time"></ion-datetime>
              <ion-label>Hora de inicio del horario</ion-label>
            <ion-datetime v-model="horarioFin" id="horarioFin" name="horarioFin"
              presentation="time"></ion-datetime>
          </div>
          <div>
            <ButtonComponent v-if="showCreated" id="created" value="Agregar" expand="full" color="warning"
              nameMethod="createRecord" @click="createRecord" />
            <ButtonComponent v-if="showUpdated" id="updated" value="Modificar" expand="full" color="warning"
              nameMethod="updateRecord" @click="updateRecord" />
          </div>
        </div>
      </ion-modal>
      <ion-grid class="data-table">
        <ion-row class="header-row">
          <ion-col>Nombre</ion-col>
          <ion-col>Especialidad</ion-col>
          <ion-col>Inicio de horario</ion-col>
          <ion-col>Fin de horario</ion-col>
          <ion-col>Acciones</ion-col>
        </ion-row>
        <ion-row v-for="especialista in especialistas" :key="especialista.id" class="data-row">
          <ion-col>{{ especialista.nombre }}</ion-col>
          <ion-col>{{ especialista.especialidad.nombre }}</ion-col>
          <ion-col>{{ especialista.horarioInicio }}</ion-col>
          <ion-col>{{ especialista.horarioFin }}</ion-col>
          <ion-col>
            <ion-button @click="fetchRecordById(especialista.id)">
              <ion-icon :icon="IonIcons.createOutline"></ion-icon>
            </ion-button>
            <ion-button @click="deleteRecordPhysical(especialista.id)">
              <ion-icon :icon="IonIcons.trash"></ion-icon>
            </ion-button>

          </ion-col>
        </ion-row>
      </ion-grid>
      <ion-button @click="openModalAdd">
        <ion-icon :icon="IonIcons.addSharp"></ion-icon>
      </ion-button>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar, IonLabel, IonModal, IonGrid, IonRow, IonCol, IonButton, IonDatetime, IonSelect, IonSelectOption } from '@ionic/vue';
import InputComponent from '@/components/InputComponent.vue';
import ButtonComponent from '@/components/ButtonComponent.vue';
import * as IonIcons from 'ionicons/icons';
import axios from 'axios';
import { onMounted, ref } from 'vue';


// Rutas de la API
const baseURLEspecialista = 'http://localhost:8080/especialista';
const baseURLEspecialidad = 'http://localhost:8080/especialidad';

const modalIsOpen = ref(false);
const especialistas = ref<Array<EspecialistaType>>([]);
const especialidades = ref<Array<EspecialidadType>>([]);
const id = ref('');
const nombre = ref('');
const especialidad = ref('');
const horarioInicio = ref('');
const horarioFin = ref('');

const showCreated = ref<boolean>(true);
const showUpdated = ref<boolean>(false);

// Definir las propiedades que controlarán la visibilidad de los botones
const props = defineProps({
  showCreated: { type: Boolean, default: true },
  showUpdated: { type: Boolean, default: false },
});

// Tipos
interface EspecialistaType {
  id: string;
  nombre: string;
  especialidad: number;
  horarioInicio: Date;
  horarioFin: Date;
}

interface EspecialidadType {
  id: string;
  nombre: string;
}

onMounted(() => {
  findAllRecords();
});

// Métodos para interactuar con la API
// Obtener todos los registros
async function findAllRecords() {
  try {
    const responseEspecialista = await axios.get(baseURLEspecialista);
    const responseEspecialidad = await axios.get(baseURLEspecialidad);
    especialistas.value = responseEspecialista.data;
    especialidades.value = responseEspecialidad.data;
  } catch (error) {
    console.error('Error al obtener todos los registros:', error);
    throw error;
  }
}

// Obtener por ID
async function fetchRecordById(recordId: string) {
  try {
    await openModalAdd();
    const response = await axios.get(`${baseURLEspecialista}/${recordId}`);
    const data = response.data;
    id.value = data.id;
    nombre.value = data.nombre;
    especialidad.value = data.especialidad.id;
    horarioInicio.value = truncateTimeFromTimestamp(data.horarioInicio);
    horarioFin.value = truncateTimeFromTimestamp(data.horarioFin);
    showCreated.value = false;
    showUpdated.value = true;
    return response.data;
  } catch (error) {
    console.error('Error al obtener el registro por ID:', error);
    throw error;
  }
}

// Crear registro
async function createRecord() {
  const data = {
    nombre: nombre.value,
    especialidad: {
      id: especialidad.value
    },
    horarioInicio: horarioInicio.value,
    horarioFin: horarioFin.value,
  };

  try {
    const response = await axios.post(baseURLEspecialista, data);
    console.log('Registro creado exitosamente:', response.data);
    await findAllRecords();
    await clearData();
    await closeModal();
  } catch (error) {
    console.error('Error al crear el registro:', error);
  }
}


// Actualizar registro
async function updateRecord() {
  const data = {
    id: id.value,
    nombre: nombre.value,
    especialidad: {
      id: especialidad.value
    },
    horarioInicio: horarioInicio.value,
    horarioFin: horarioFin.value,
  };
  try {
    const response = await axios.put(`${baseURLEspecialista}/${data.id}`, data);
    await closeModal();
    await findAllRecords();
    await clearData();

    showCreated.value = true;
    showUpdated.value = false;

    return response.data;
  } catch (error) {
    console.error('Error al actualizar el registro:', error);
    throw error;
  }
}

// Eliminar registro físico
async function deleteRecordPhysical(id: string) {
  try {
    const response = await axios.delete(`${baseURLEspecialista}/${id}`);
    await findAllRecords();
    return response.data;
  } catch (error) {
    console.error('Error al eliminar el registro físico:', error);
    throw error;
  }
}

async function clearData() {
  nombre.value = '';
  especialidad.value = '';
  horarioInicio.value = '';
  horarioFin.value = '';
}

const openModalAdd = () => {
  modalIsOpen.value = true;
};

const closeModal = () => {
  modalIsOpen.value = false;
};

function truncateTimeFromTimestamp(timestamp) {
  const date = new Date(timestamp);
  date.setHours(0, 0, 0, 0);
  return date;
}

</script>

<style scoped>
.data-table {
  width: 100%;
  border-collapse: collapse;
}

.header-row,
.data-row {
  border-bottom: 1px solid #ccc;
}

ion-col {
  padding: 10px;
  border-right: 1px solid #ccc;
}

ion-col:last-child {
  border-right: none;
}

.header-row ion-col {
  font-weight: bold;
}
</style>
