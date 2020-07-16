package by.itstep.onlineauctionsystem.dto;

import by.itstep.onlineauctionsystem.validation.PasswordMatches;
import by.itstep.onlineauctionsystem.validation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
}
