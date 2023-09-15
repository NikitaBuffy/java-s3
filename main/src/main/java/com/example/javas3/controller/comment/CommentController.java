package com.example.javas3.controller.comment;

import com.example.javas3.dto.comment.CommentDto;
import com.example.javas3.dto.comment.NewCommentDto;
import com.example.javas3.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class CommentController {

    private CommentService commentService;

    @PostMapping("/users/{userId}")
    public CommentDto addComment(@PathVariable Long userId, @Validated @RequestBody NewCommentDto newCommentDto) {
        return commentService.addComment(userId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    @GetMapping
    public List<CommentDto> getComments(@RequestParam(defaultValue = "NEWEST_FIRST") String sort,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return commentService.getComments(sort, from, size);
    }
}
