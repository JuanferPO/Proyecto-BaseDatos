package ProyectoBD.App.Servicio;

import ProyectoBD.App.Modelo.Procedimiento;
import ProyectoBD.App.Modelo.Procedimiento;
import ProyectoBD.App.Repositorio.ProcedimientoRepositorio;
import ProyectoBD.App.Repositorio.ProcedimientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProcedimientoServicio {

    @Autowired
    private ProcedimientoRepositorio repository;


    public Procedimiento save(Procedimiento Procedimiento) {
        return repository.save(Procedimiento);
    }

    public void update(Procedimiento procedimiento, int id) {
        Optional<Procedimiento> optional = repository.findById(id);

        if (optional.isPresent()) {
            Procedimiento procedimientoUpdate = optional.get();
            procedimientoUpdate.setNombre(procedimiento.getNombre());
            procedimientoUpdate.setEspecialidad(procedimiento.getEspecialidad());
            repository.save(procedimientoUpdate);
        }else{
            throw new RuntimeException("No existe el registro para actualizar");
        }

    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Optional<Procedimiento> findById(int id) {
        return repository.findById(id);
    }

    public List<Procedimiento> findAll() {
        return repository.findAll();
    }
}
