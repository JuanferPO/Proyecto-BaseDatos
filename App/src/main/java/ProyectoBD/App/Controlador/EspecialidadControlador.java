package ProyectoBD.App.Controlador;

import ProyectoBD.App.Modelo.Especialidad;
import ProyectoBD.App.Servicio.EspecialidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("especialidad")
public class EspecialidadControlador {

    @Autowired
    private EspecialidadServicio service;

    @PostMapping("")
    public Especialidad save(@RequestBody Especialidad especialidad){
        return service.save(especialidad);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Especialidad especialidad, @PathVariable int id){
        service.update(especialidad, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @GetMapping("")
    public List<Especialidad> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Especialidad> findById(@PathVariable int id){
        return service.findById(id);
    }
}
