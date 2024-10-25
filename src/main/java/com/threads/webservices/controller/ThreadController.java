package com.threads.webservices.controller;

import com.threads.webservices.dto.request.ApiResponse;
import com.threads.webservices.dto.request.ThreadCreationRequest;
import com.threads.webservices.dto.request.ThreadUpdateRequest;
import com.threads.webservices.dto.response.ThreadListResponse;
import com.threads.webservices.dto.response.ThreadResponse;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @PostMapping("/repost")
    public ApiResponse<?> repost(
            @RequestParam("thread_id") String threadId,
            @RequestParam("user_id") String userId
    ) {
        threadService.repost(threadId, userId);
        return ApiResponse.<String>builder()
                .result("Thread has been reposted!")
                .build();
    }

    @GetMapping("/page")
    public ApiResponse<?> getAllThreads(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
            page, limit, Sort.by("id").ascending() //Sắp xếp theo ngày tạo giảm dần
        );

        Page<Thread> threadsPage = threadService.findThreads(pageRequest);
        Page<ThreadResponse> threadResponsePage = threadsPage.map(ThreadResponse::fromThread);

        ThreadListResponse threadListResponse = ThreadListResponse.builder()
                .totalPages(threadResponsePage.getTotalPages())
                .threads(threadResponsePage.getContent())
                .build();

        return ApiResponse.builder()
                .result(threadListResponse)
                .build();
    }

}
