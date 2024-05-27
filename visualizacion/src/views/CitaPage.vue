<!-- Componente principal donde se utiliza CrudButtonComponent -->
<template>
  <ion-page>
    <ion-header :translucent="true">
      <ion-toolbar>
        <ion-title>Citas</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <ion-modal :is-open="modalIsOpen" @didDismiss="modalIsOpen = false" :css-class="['my-custom-modal']">
        <div id="modal-content">
          <div id="data-form">
            <InputComponent v-model="nombrePaciente" id="nombrePaciente" name="nombrePaciente" label="Paciente: " />
            <ion-select v-model="procedimiento" id="procedimiento" name="procedimiento" label="Procedimiento: ">
              <ion-select-option v-for="procedimiento in procedimientos" :key="procedimiento.id"
                :value="procedimiento.id">{{ procedimiento.nombre }}</ion-select-option>
            </ion-select>
            <ion-select v-model="especialista" id="especialista" name="especialista" label="Especialista: ">
              <ion-select-option v-for="especialista in especialistas" :key="especialista.id"
                :value="especialista.id">{{ especialista.nombre }}</ion-select-option>
            </ion-select>
            <ion-select v-model="consultorio" id="consultorio" name="consultorio" label="Consultorio: ">
              <ion-select-option v-for="consultorio in consultorios" :key="consultorio.id" :value="consultorio.id">{{
      consultorio.ubicacion }}</ion-select-option>
            </ion-select>
            <ion-label>Fecha y hora de atención</ion-label>
            <ion-datetime v-model="fechaHoraAtencion" id="fechaHoraAtencion" name="fechaHoraAtencion"></ion-datetime>
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
          <ion-col>Paciente</ion-col>
          <ion-col>Procedimiento</ion-col>
          <ion-col>Especialista</ion-col>
          <ion-col>Consultorio</ion-col>
          <ion-col>Fecha y hora de atención</ion-col>
          <ion-col>Acciones</ion-col>
        </ion-row>
        <ion-row v-for="cita in citas" :key="cita.id" class="data-row">
          <ion-col>{{ cita.nombrePaciente }}</ion-col>
          <ion-col>{{ cita.procedimiento.nombre }}</ion-col>
          <ion-col>{{ cita.especialista.nombre }}</ion-col>
          <ion-col>{{ cita.consultorio.ubicacion }}</ion-col>
          <ion-col>{{ cita.fechaHoraAtencion }}</ion-col>
          <ion-col>
            <ion-button @click="fetchRecordById(cita.id)">
              <ion-icon :icon="IonIcons.createOutline"></ion-icon>
            </ion-button>
            <ion-button @click="deleteRecordPhysical(cita.id)">
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
const baseURLCita = 'http://localhost:8080/cita';
const baseURLProcedimiento = 'http://localhost:8080/procedimiento';
const baseURLEspecialista = 'http://localhost:8080/especialista';
const baseURLConsultorio = 'http://localhost:8080/consultorio';

const modalIsOpen = ref(false);
const citas = ref<Array<CitaType>>([]);
const procedimientos = ref<Array<ProcedimientoType>>([]);
const especialistas = ref<Array<EspecialistaType>>([]);
const consultorios = ref<Array<ConsultorioType>>([]);
const id = ref('');
const nombrePaciente = ref('');
const procedimiento = ref('');
const especialista = ref('');
const consultorio = ref('');
const fechaHoraAtencion = ref('');

const showCreated = ref<boolean>(true);
const showUpdated = ref<boolean>(false);

// Definir las propiedades que controlarán la visibilidad de los botones
const props = defineProps({
  showCreated: { type: Boolean, default: true },
  showUpdated: { type: Boolean, default: false },
});

// Tipos
interface CitaType {
  id: string;
  nombrePaciente: string;
  procedimiento: number;
  especialista: number;
  consultorio: number;
  fechaHoraAtencion: string;
}

interface ProcedimientoType {
  id: string;
  nombre: string;
  especialidad: number;
}

interface EspecialistaType {
  id: string;
  nombre: string;
  especialidad: number;
  horarioInicio: string;
  horarioFin: string;
}

interface ConsultorioType {
  id: string;
  ubicacion: string;
  departamento: number;
}

onMounted(() => {
  findAllRecords();
});

// Métodos para interactuar con la API
// Obtener todos los registros
async function findAllRecords() {
  try {
    const responseCita = await axios.get(baseURLCita);
    const responseProcedimiento = await axios.get(baseURLProcedimiento);
    const responseEspecialista = await axios.get(baseURLEspecialista);
    const responseConsultorio = await axios.get(baseURLConsultorio);
    citas.value = responseCita.data;
    procedimientos.value = responseProcedimiento.data;
    especialistas.value = responseEspecialista.data;
    consultorios.value = responseConsultorio.data;
  } catch (error) {
    console.error('Error al obtener todos los registros:', error);
    throw error;
  }
}

// Obtener por ID
async function fetchRecordById(recordId: string) {
  try {
    await openModalAdd();
    const response = await axios.get(`${baseURLCita}/${recordId}`);
    const data = response.data;
    id.value = data.id;
    nombrePaciente.value = data.nombrePaciente;
    procedimiento.value = data.procedimiento.id;
    especialista.value = data.especialista.id;
    consultorio.value = data.consultorio.id;
    fechaHoraAtencion.value = data.categoria;
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
    nombrePaciente: nombrePaciente.value,
    procedimiento: {
      id: procedimiento.value
    },
    especialista: {
      id: especialista.value
    },
    consultorio: {
      id: consultorio.value
    },
    fechaHoraAtencion: fechaHoraAtencion.value,
  };

  try {
    const response = await axios.post(baseURLCita, data);
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
    nombrePaciente: nombrePaciente.value,
    procedimiento: {
      id: procedimiento.value
    },
    especialista: {
      id: especialista.value
    },
    consultorio: {
      id: consultorio.value
    },
    fechaHoraAtencion: fechaHoraAtencion.value,
  };
  try {
    const response = await axios.put(`${baseURLCita}/${data.id}`, data);
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
    const response = await axios.delete(`${baseURLCita}/${id}`);
    await findAllRecords();
    return response.data;
  } catch (error) {
    console.error('Error al eliminar el registro físico:', error);
    throw error;
  }
}

async function clearData() {
  nombrePaciente.value = '';
  procedimiento.value = '';
  especialista.value = '';
  consultorio.value = '';
  fechaHoraAtencion.value = '';
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
