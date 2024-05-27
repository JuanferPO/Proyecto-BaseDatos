package ProyectoBD.App.Servicio;

import ProyectoBD.App.Modelo.Departamento;
import ProyectoBD.App.Modelo.Departamento;
import ProyectoBD.App.Repositorio.DepartamentoRepositorio;
import ProyectoBD.App.Repositorio.DepartamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoServicio {

    @Autowired
    private DepartamentoRepositorio repository;


    public Departamento save(Departamento Departamento) {
        return repository.save(Departamento);
    }

    public void update(Departamento departamento, int id) {
        Optional<Departamento> optional = repository.findById(id);

        if (optional.isPresent()) {
            Departamento departamentoUpdate = optional.get();
            departamentoUpdate.setNombre(departamento.getNombre());
            repository.save(departamentoUpdate);
        }else{
            throw new RuntimeException("No existe el registro para actualizar");
        }

    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Optional<Departamento> findById(int id) {
        return repository.findById(id);
    }

    public List<Departamento> findAll() {
        return repository.findAll();
    }
}
