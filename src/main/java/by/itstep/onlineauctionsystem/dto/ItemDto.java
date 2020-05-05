package by.itstep.onlineauctionsystem.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ItemDto {
    private long id;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String price;
    @NotNull
    @NotEmpty
    private String increment;
    @NotNull
    @NotEmpty
    private String startTime;
    @NotNull
    @NotEmpty
    private String endTime;
    private String formattedEndTime;
    private String email;
    private String bidCount;
    @NotNull
    @NotEmpty
    private MultipartFile[] images;
    private List<String> encodedImages;
}
