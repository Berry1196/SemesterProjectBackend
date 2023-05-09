package dtos;

import entities.Exercise;
import entities.Workout;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExerciseDTO {
    private Long id;
    private String name;
    private String type;
    private String muscle;
    private String equipment;
    private String difficulty;
    private String instructions;

    public ExerciseDTO(Exercise exercise) {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.type = exercise.getType();
        this.muscle = exercise.getMuscle();
        this.equipment = exercise.getEquipment();
        this.difficulty = exercise.getDifficulty();
        this.instructions = exercise.getInstructions();
    }

    public static List<ExerciseDTO> getDTOs(List<Exercise> exercises){
        List<ExerciseDTO> exerciseDTOs = new ArrayList<>();
        exercises.forEach(exercise -> exerciseDTOs.add(new ExerciseDTO(exercise)));
        return exerciseDTOs;
    }
}
