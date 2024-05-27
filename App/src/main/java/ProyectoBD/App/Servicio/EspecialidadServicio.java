package ProyectoBD.App.Servicio;

import ProyectoBD.App.Modelo.Especialidad;
import ProyectoBD.App.Modelo.Especialidad;
import ProyectoBD.App.Repositorio.EspecialidadRepositorio;
import ProyectoBD.App.Repositorio.EspecialidadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadServicio {

    @Autowired
    private EspecialidadRepositorio repository;


    public Especialidad save(Especialidad Especialidad) {
        return repository.save(Especialidad);
    }

    public void update(Especialidad especialidad, int id) {
        Optional<Especialidad> optional = repository.findById(id);

        if (optional.isPresent()) {
            Especialidad especialidadUpdate = optional.get();
            especialidadUpdate.setNombre(especialidad.getNombre());
            repository.save(especialidadUpdate);
        }else{
            throw new RuntimeException("No existe el registro para actualizar");
        }

    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Optional<Especialidad> findById(int id) {
        return repository.findById(id);
    }

    public List<Especialidad> findAll() {
        return repository.findAll();
    }
}
