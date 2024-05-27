package ProyectoBD.App.Controlador;

import ProyectoBD.App.Modelo.Cita;
import ProyectoBD.App.Servicio.CitaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("cita")
public class CitaControlador {

    @Autowired
    private CitaServicio service;

    @PostMapping("")
    public Cita save(@RequestBody Cita cita){
        return service.save(cita);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Cita cita, @PathVariable int id){
        service.update(cita, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @GetMapping("")
    public List<Cita> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cita> findById(@PathVariable int id){
        return service.findById(id);
    }
}
