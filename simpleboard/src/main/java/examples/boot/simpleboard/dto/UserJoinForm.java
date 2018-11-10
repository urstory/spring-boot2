package examples.boot.simpleboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class UserJoinForm {
    @NotNull
    @Size(min=2, max = 255)
    private String name;
    @NotNull
    @Size(min=5, max = 255)
    private String email;
    @NotNull
    @Size(min=8, max = 16)
    private String password;
    @NotNull
    @Size(min=8, max = 16)
    private String rePassword;
}
