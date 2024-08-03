package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDataFull {
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String registerDate;
    private String updatedDate;
    private String picture;
    private String title;

}
