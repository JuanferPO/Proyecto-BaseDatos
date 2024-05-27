<template>
  <ion-page>
    <ion-header :translucent="true">
      <ion-toolbar>
        <ion-title>Departamentos</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <ion-modal :is-open="modalIsOpen" @didDismiss="modalIsOpen = false" :css-class="['my-custom-modal']">
        <div id="modal-content">
          <div id="data-form">
            <InputComponent v-model="nombre" id="nombre" name="nombre" label="Nombre: " />
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
          <ion-col>Acciones</ion-col>
        </ion-row>
        <ion-row v-for="departamento in departamentos" :key="departamento.id" class="data-row">
          <ion-col>{{ departamento.nombre }}</ion-col>
          <ion-col>
            <ion-button @click="fetchRecordById(departamento.id)">
              <ion-icon :icon="IonIcons.createOutline"></ion-icon>
            </ion-button>
            <ion-button @click="deleteRecordPhysical(departamento.id)">
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
const baseURLDepartamento = 'http://localhost:8080/departamento';

const modalIsOpen = ref(false);
const departamentos = ref<Array<DepartamentoType>>([]);
const id = ref('');
const nombre = ref('');

const showCreated = ref<boolean>(true);
const showUpdated = ref<boolean>(false);

// Definir las propiedades que controlarán la visibilidad de los botones
const props = defineProps({
  showCreated: { type: Boolean, default: true },
  showUpdated: { type: Boolean, default: false },
});

// Tipos
interface DepartamentoType {
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
    const responseDepartamento = await axios.get(baseURLDepartamento);
    departamentos.value = responseDepartamento.data;
  } catch (error) {
    console.error('Error al obtener todos los registros:', error);
    throw error;
  }
}

// Obtener por ID
async function fetchRecordById(recordId: string) {
  try {
    await openModalAdd();
    const response = await axios.get(`${baseURLDepartamento}/${recordId}`);
    const data = response.data;
    id.value = data.id;
    nombre.value = data.nombre;
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
    nombre: nombre.value
  };

  try {
    const response = await axios.post(baseURLDepartamento, data);
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
    nombre: nombre.value
  };
  try {
    const response = await axios.put(`${baseURLDepartamento}/${data.id}`, data);
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
    const response = await axios.delete(`${baseURLDepartamento}/${id}`);
    await findAllRecords();
    return response.data;
  } catch (error) {
    console.error('Error al eliminar el registro físico:', error);
    throw error;
  }
}

async function clearData() {
  nombre.value = '';
}

const openModalAdd = () => {
  modalIsOpen.value = true;
};

const closeModal = () => {
  modalIsOpen.value = false;
};
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
