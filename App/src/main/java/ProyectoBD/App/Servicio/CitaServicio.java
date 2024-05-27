package ProyectoBD.App.Servicio;

import ProyectoBD.App.Repositorio.CitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ProyectoBD.App.Modelo.Cita;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServicio {

    @Autowired
    private CitaRepositorio repository;


    public Cita save(Cita Cita) {
        return repository.save(Cita);
    }

    public void update(Cita cita, int id) {
        Optional<Cita> optional = repository.findById(id);

        if (optional.isPresent()) {
            Cita citaUpdate = optional.get();
            citaUpdate.setNombrePaciente(cita.getNombrePaciente());
            citaUpdate.setProcedimiento(cita.getProcedimiento());
            citaUpdate.setEspecialista(cita.getEspecialista());
            citaUpdate.setConsultorio(cita.getConsultorio());
            citaUpdate.setFechaHoraAtencion(cita.getFechaHoraAtencion());
            repository.save(citaUpdate);
        }else{
            throw new RuntimeException("No existe el registro para actualizar");
        }

    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Optional<Cita> findById(int id) {
        return repository.findById(id);
    }

    public List<Cita> findAll() {
        return repository.findAll();
    }
}
