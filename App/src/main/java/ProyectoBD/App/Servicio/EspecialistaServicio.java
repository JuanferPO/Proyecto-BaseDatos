package ProyectoBD.App.Servicio;

import ProyectoBD.App.Modelo.Especialista;
import ProyectoBD.App.Modelo.Especialista;
import ProyectoBD.App.Repositorio.EspecialistaRepositorio;
import ProyectoBD.App.Repositorio.EspecialistaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialistaServicio {

    @Autowired
    private EspecialistaRepositorio repository;


    public Especialista save(Especialista Especialista) {
        return repository.save(Especialista);
    }

    public void update(Especialista especialista, int id) {
        Optional<Especialista> optional = repository.findById(id);

        if (optional.isPresent()) {
            Especialista especialistaUpdate = optional.get();
            especialistaUpdate.setNombre(especialista.getNombre());
            especialistaUpdate.setEspecialidad(especialista.getEspecialidad());
            especialistaUpdate.setHorarioInicio(especialista.getHorarioInicio());
            especialistaUpdate.setHorarioFin(especialista.getHorarioFin());
            repository.save(especialistaUpdate);
        }else{
            throw new RuntimeException("No existe el registro para actualizar");
        }

    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Optional<Especialista> findById(int id) {
        return repository.findById(id);
    }

    public List<Especialista> findAll() {
        return repository.findAll();
    }
}
