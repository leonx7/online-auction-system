package by.itstep.onlineauctionsystem.model.category;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {
    @NotEmpty
    @NotNull
    private String name;
    private String parentId;
    private String upperBound;
    private String lowerBound;
}
