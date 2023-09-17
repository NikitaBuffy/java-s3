package com.example.javas3.service.comment;

import com.example.javas3.dto.comment.CommentDto;
import com.example.javas3.dto.comment.NewCommentDto;
import com.example.javas3.model.comment.Comment;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Long userId, NewCommentDto newCommentDto);

    void deleteComment(Long commentId);

    CommentDto getComment(Long commentId);

    List<CommentDto> getComments(String sort, int from, int size);

    Comment getExistingComment(Long commentId);
}
