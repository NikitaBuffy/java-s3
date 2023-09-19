package com.example.javas3.controller.comment;

import com.example.javas3.dto.comment.CommentDto;
import com.example.javas3.dto.comment.NewCommentDto;
import com.example.javas3.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * Создание нового комментария
     * @param userId ID пользователя
     * @param newCommentDto Данные добавляемого комментария
     * @return Созданный комментарий
     */
    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Long userId, @Validated @ModelAttribute NewCommentDto newCommentDto) {
        return commentService.addComment(userId, newCommentDto);
    }

    /**
     * Удаление комментария по его ID
     * @param commentId ID комментария
     */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    /**
     * Получение комментария по его ID
     * @param commentId ID комментария
     * @return Комментарий
     */
    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    /**
     * Получение всех комментариев с возможностью сортировки
     * @param sort Вариант сортировки: по дате комментария, и по рейтингу (NEWEST_FIRST, POSITIVE_FIRST, NEGATIVE_FIRST)
     * @param from Количество комментариев, которые нужно пропустить для формирования текущего набора
     * @param size Количество комментариев в наборе
     * @return Список комментариев
     */
    @GetMapping
    public List<CommentDto> getComments(@RequestParam(defaultValue = "NEWEST_FIRST") String sort,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return commentService.getComments(sort, from, size);
    }
}
