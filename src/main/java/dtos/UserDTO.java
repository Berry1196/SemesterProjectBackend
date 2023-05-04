package dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private String user_name;
    private String user_pass;

    public UserDTO(String user_name, String user_pass) {
        this.user_name = user_name;
        this.user_pass = user_pass;
    }
}
