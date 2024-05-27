package ProyectoBD.App.Controlador;

import ProyectoBD.App.Modelo.Especialista;
import ProyectoBD.App.Servicio.EspecialistaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("especialista")
public class EspecialistaControlador {

    @Autowired
    private EspecialistaServicio service;

    @PostMapping("")
    public Especialista save(@RequestBody Especialista especialista){
        return service.save(especialista);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Especialista especialista, @PathVariable int id){
        service.update(especialista, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @GetMapping("")
    public List<Especialista> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Especialista> findById(@PathVariable int id){
        return service.findById(id);
    }
}
