<template>
  <ion-page>
    <ion-header :translucent="true">
      <ion-toolbar>
        <ion-title>Consultorios</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <ion-modal :is-open="modalIsOpen" @didDismiss="modalIsOpen = false" :css-class="['my-custom-modal']">
        <div id="modal-content">
          <div id="data-form">
            <InputComponent v-model="ubicacion" id="ubicacion" name="ubicacion" label="Ubicacion: " />
            <ion-select v-model="departamento" id="departamento" name="departamento" label="Departamento: ">
              <ion-select-option v-for="departamento in departamentos" :key="departamento.id"
                :value="departamento.id">{{ departamento.nombre }}</ion-select-option>
            </ion-select>
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
          <ion-col>Departamento</ion-col>
          <ion-col>Acciones</ion-col>
        </ion-row>
        <ion-row v-for="consultorio in consultorios" :key="consultorio.id" class="data-row">
          <ion-col>{{ consultorio.ubicacion }}</ion-col>
          <ion-col>{{ consultorio.departamento.nombre }}</ion-col>
          <ion-col>
            <ion-button @click="fetchRecordById(consultorio.id)">
              <ion-icon :icon="IonIcons.createOutline"></ion-icon>
            </ion-button>
            <ion-button @click="deleteRecordPhysical(consultorio.id)">
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
const baseURLConsultorio = 'http://localhost:8080/consultorio';
const baseURLDepartamento = 'http://localhost:8080/departamento';

const modalIsOpen = ref(false);
const consultorios = ref<Array<ConsultorioType>>([]);
const departamentos = ref<Array<DepartamentoType>>([]);
const id = ref('');
const ubicacion = ref('');
const departamento = ref('');

const showCreated = ref<boolean>(true);
const showUpdated = ref<boolean>(false);

// Definir las propiedades que controlarán la visibilidad de los botones
const props = defineProps({
  showCreated: { type: Boolean, default: true },
  showUpdated: { type: Boolean, default: false },
});

// Tipos
interface ConsultorioType {
  id: string;
  ubicacion: string;
  departamento: number;
}

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
    const responseConsultorio = await axios.get(baseURLConsultorio);
    const responseDepartamento = await axios.get(baseURLDepartamento);
    consultorios.value = responseConsultorio.data;
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
    const response = await axios.get(`${baseURLConsultorio}/${recordId}`);
    const data = response.data;
    id.value = data.id;
    ubicacion.value = data.nombre;
    departamento.value = data.departamento.id;
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
    ubicacion: ubicacion.value,
    departamento: {
      id: departamento.value
    }
  };

  try {
    const response = await axios.post(baseURLConsultorio, data);
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
    ubicacion: ubicacion.value,
    departamento: {
      id: departamento.value
    }
  };
  try {
    const response = await axios.put(`${baseURLConsultorio}/${data.id}`, data);
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
    const response = await axios.delete(`${baseURLConsultorio}/${id}`);
    await findAllRecords();
    return response.data;
  } catch (error) {
    console.error('Error al eliminar el registro físico:', error);
    throw error;
  }
}

async function clearData() {
  ubicacion.value = '';
  departamento.value = '';
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
