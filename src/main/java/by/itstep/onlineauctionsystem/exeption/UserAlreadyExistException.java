package by.itstep.onlineauctionsystem.exeption;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserAlreadyExistException extends Throwable {
    public UserAlreadyExistException(@NotNull @NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
