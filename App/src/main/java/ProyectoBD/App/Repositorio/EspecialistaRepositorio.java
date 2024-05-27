package ProyectoBD.App.Repositorio;

import ProyectoBD.App.Modelo.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialistaRepositorio extends JpaRepository<Especialista, Integer> {
}
