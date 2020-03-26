package by.itstep.onlineauctionsystem.exeption;

public class UsernameExistsException extends Exception {
    public UsernameExistsException(String errorMessage) {
        super(errorMessage);
    }
}
