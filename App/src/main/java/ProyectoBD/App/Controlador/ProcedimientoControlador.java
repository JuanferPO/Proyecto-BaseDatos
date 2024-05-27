package ProyectoBD.App.Controlador;

import ProyectoBD.App.Modelo.Procedimiento;
import ProyectoBD.App.Servicio.ProcedimientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("procedimiento")
public class ProcedimientoControlador {

    @Autowired
    private ProcedimientoServicio service;

    @PostMapping("")
    public Procedimiento save(@RequestBody Procedimiento procedimiento){
        return service.save(procedimiento);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Procedimiento procedimiento, @PathVariable int id){
        service.update(procedimiento, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @GetMapping("")
    public List<Procedimiento> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Procedimiento> findById(@PathVariable int id){
        return service.findById(id);
    }
}
