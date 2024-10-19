package com.threads.webservices.controller;

import com.threads.webservices.dto.request.ApiResponse;
import com.threads.webservices.dto.request.ThreadCreationRequest;
import com.threads.webservices.dto.request.ThreadUpdateRequest;
import com.threads.webservices.dto.response.ThreadResponse;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/threads")
public class ThreadController {
    @Autowired
    private ThreadService threadService;

    @PostMapping
    public ApiResponse<ThreadResponse> createThread(@RequestBody ThreadCreationRequest request){
        return ApiResponse.<ThreadResponse>builder()
                .result(threadService.createThread(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ThreadResponse>> getUserThreads(){
        return ApiResponse.<List<ThreadResponse>>builder()
                .result(threadService.getThreadsByUser())
                .build();
    }

    @PutMapping("/{threadId}")
    public ApiResponse<ThreadResponse> updateThread(@PathVariable String threadId, @RequestBody ThreadUpdateRequest request){
        return ApiResponse.<ThreadResponse>builder()
                .result(threadService.updateThread(threadId, request))
                .build();
    }

    @DeleteMapping("/{threadId}")
    public ApiResponse<String> deleteThread(@PathVariable String threadId){
        threadService.deleteThread(threadId);
        return ApiResponse.<String>builder()
                .result("Thread has been deleted!")
                .build();
    }
}
