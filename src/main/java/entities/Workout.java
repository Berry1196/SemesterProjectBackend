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
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    @JoinTable(name = "user_workouts", joinColumns = {
            @JoinColumn(name = "workout_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")})
    @ManyToMany
    private List<User> userList = new ArrayList<>();
}
