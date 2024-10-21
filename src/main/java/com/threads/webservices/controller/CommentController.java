package com.threads.webservices.controller;

import com.threads.webservices.dto.request.ApiResponse;
import com.threads.webservices.dto.request.ThreadCreationRequest;
import com.threads.webservices.dto.response.ThreadResponse;
import com.threads.webservices.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ApiResponse<ThreadResponse> createComment(@RequestBody ThreadCreationRequest request){
        return ApiResponse.<ThreadResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ThreadResponse>> getUserComments(){
        return ApiResponse.<List<ThreadResponse>>builder()
                .result(commentService.getUserComments())
                .build();
    }

    @GetMapping("/{threadId")
    public ApiResponse<List<ThreadResponse>> getCommentByThreadId(@PathVariable String threadId){
        return ApiResponse.<List<ThreadResponse>>builder()
                .result(commentService.getCommentByThreadId(threadId))
                .build();
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<String> deleteComment(@PathVariable String commentId){
        return ApiResponse.<String>builder()
                .result("Comment has been deleted!")
                .build();
    }
}
