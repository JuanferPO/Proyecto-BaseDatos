package ProyectoBD.App.Controlador;

import ProyectoBD.App.Modelo.Departamento;
import ProyectoBD.App.Servicio.DepartamentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("departamento")
public class DepartamentoControlador {

    @Autowired
    private DepartamentoServicio service;

    @PostMapping("")
    public Departamento save(@RequestBody Departamento departamento){
        return service.save(departamento);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Departamento departamento, @PathVariable int id){
        service.update(departamento, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @GetMapping("")
    public List<Departamento> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Departamento> findById(@PathVariable int id){
        return service.findById(id);
    }
}
