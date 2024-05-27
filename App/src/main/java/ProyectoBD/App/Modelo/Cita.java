package ProyectoBD.App.Modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    private String nombrePaciente;
    @ManyToOne
    @JoinColumn(name = "id_procedimiento")
    private Procedimiento procedimiento;
    @ManyToOne
    @JoinColumn(name = "id_especialista")
    private Especialista especialista;
    @ManyToOne
    @JoinColumn(name = "id_consultorio")
    private Consultorio consultorio;
    private LocalDateTime fechaHoraAtencion;
}
