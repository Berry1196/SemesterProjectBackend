package dtos;

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
public class WorkoutDTO {
    private Long id;
    private String name;
    private List<ExerciseDTO> exercisesList = new ArrayList<>();

    public WorkoutDTO(Workout workout) {
        this.id = workout.getId();
        this.name = workout.getName();
        this.exercisesList = ExerciseDTO.getDTOs(workout.getExercisesList());
    }

    public static List<WorkoutDTO> getDTOs(List<Workout> workouts){
        List<WorkoutDTO> workoutDTOs = new ArrayList<>();
        workouts.forEach(workout -> workoutDTOs.add(new WorkoutDTO(workout)));
        return workoutDTOs;
    }
}
