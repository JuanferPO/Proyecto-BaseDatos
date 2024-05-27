package ProyectoBD.App.Repositorio;

import ProyectoBD.App.Modelo.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultorioRepositorio extends JpaRepository<Consultorio, Integer> {
}
