package entities;

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
@ToString
@Entity
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="name")
    private String name;

    @JoinTable(name = "workout_exercises", joinColumns = {
            @JoinColumn(name = "exercise_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "workout_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Workout> workoutList = new ArrayList<>();
}
