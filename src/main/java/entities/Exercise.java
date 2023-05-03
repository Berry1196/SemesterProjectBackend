package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="type")
    private String type;
    @Column(name="muscle")
    private String muscle;
    @Column(name="equipment")
    private String equipment;
    @Column(name="difficulty")
    private String difficulty;
    @Column(name="instructions")
    private String instructions;

    @JoinTable(name = "workout_exercises", joinColumns = {
            @JoinColumn(name = "exercises_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "workout_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Workout> workoutList = new ArrayList<>();
}
