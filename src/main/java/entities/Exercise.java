package entities;

import dtos.ExerciseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
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

    public Exercise(ExerciseDTO exerciseDTO) {
        this.name = exerciseDTO.getName();
        this.type = exerciseDTO.getType();
        this.muscle = exerciseDTO.getMuscle();
        this.equipment = exerciseDTO.getEquipment();
        this.difficulty = exerciseDTO.getDifficulty();
        this.instructions = exerciseDTO.getInstructions();
    }
}
