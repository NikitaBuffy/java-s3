package com.example.javas3.dto.comment;

import com.example.javas3.model.comment.Comment;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class CommentMapper {

    public static Comment toComment(NewCommentDto newCommentDto) {
        return new Comment(
                null,
                newCommentDto.getText(),
                null,
                null,
                newCommentDto.getRating()
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getAuthor().getName(),
                comment.getText(),
                comment.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                comment.getRating()
        );
    }
}
