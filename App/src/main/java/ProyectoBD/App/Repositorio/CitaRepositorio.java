package ProyectoBD.App.Repositorio;

import ProyectoBD.App.Modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepositorio extends JpaRepository<Cita, Integer> {
}
