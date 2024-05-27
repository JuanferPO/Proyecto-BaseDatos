package ProyectoBD.App.Controlador;

import ProyectoBD.App.Modelo.Consultorio;
import ProyectoBD.App.Servicio.ConsultorioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("consultorio")
public class ConsultorioControlador {

    @Autowired
    private ConsultorioServicio service;

    @PostMapping("")
    public Consultorio save(@RequestBody Consultorio consultorio){
        return service.save(consultorio);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Consultorio consultorio, @PathVariable int id){
        service.update(consultorio, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @GetMapping("")
    public List<Consultorio> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Consultorio> findById(@PathVariable int id){
        return service.findById(id);
    }
}
