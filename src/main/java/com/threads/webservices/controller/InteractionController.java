package com.threads.webservices.controller;

import com.threads.webservices.dto.request.ApiResponse;
import com.threads.webservices.dto.request.InteractionRequest;
import com.threads.webservices.dto.response.InteractionResponse;
import com.threads.webservices.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interactions")
@RequiredArgsConstructor
public class InteractionController {
    private final InteractionService interactionService;

    @PostMapping("/{threadId}")
    public ApiResponse<InteractionResponse> interactWithThread(@PathVariable String threadId,
                                                               @RequestBody InteractionRequest request) {
        return ApiResponse.<InteractionResponse>builder()
                .result(interactionService.interactionWithThread(threadId, request))
                .build();
    }

    @DeleteMapping("/{threadId}")
    public ApiResponse<String> removeInteraction(@PathVariable String threadId) {
        interactionService.removeInteraction(threadId);
        return ApiResponse.<String>builder()
                .result("Interaction removed successfully.")
                .build();
    }
}
