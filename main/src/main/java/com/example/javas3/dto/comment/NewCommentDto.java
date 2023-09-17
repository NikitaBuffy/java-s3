package com.example.javas3.dto.comment;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {

    @NotNull
    @Size(min = 50, max = 2000)
    private String text;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    private List<MultipartFile> photos;
}
