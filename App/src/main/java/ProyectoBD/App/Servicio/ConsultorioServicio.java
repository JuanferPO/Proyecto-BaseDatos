package ProyectoBD.App.Servicio;

import ProyectoBD.App.Modelo.Consultorio;
import ProyectoBD.App.Repositorio.ConsultorioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultorioServicio {

    @Autowired
    private ConsultorioRepositorio repository;


    public Consultorio save(Consultorio Consultorio) {
        return repository.save(Consultorio);
    }

    public void update(Consultorio consultorio, int id) {
        Optional<Consultorio> optional = repository.findById(id);

        if (optional.isPresent()) {
            Consultorio consultorioUpdate = optional.get();
            consultorioUpdate.setUbicacion(consultorio.getUbicacion());
            consultorioUpdate.setDepartamento(consultorio.getDepartamento());
            repository.save(consultorioUpdate);
        }else{
            throw new RuntimeException("No existe el registro para actualizar");
        }

    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Optional<Consultorio> findById(int id) {
        return repository.findById(id);
    }

    public List<Consultorio> findAll() {
        return repository.findAll();
    }
}
