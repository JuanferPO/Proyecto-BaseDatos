package ProyectoBD.App.Modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Especialista")
public class Especialista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
}
