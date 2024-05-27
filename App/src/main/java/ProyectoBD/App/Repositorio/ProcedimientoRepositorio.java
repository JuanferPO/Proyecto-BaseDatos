package ProyectoBD.App.Repositorio;

import ProyectoBD.App.Modelo.Procedimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedimientoRepositorio extends JpaRepository<Procedimiento, Integer> {
}
