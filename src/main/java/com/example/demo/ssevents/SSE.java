package com.example.demo.ssevents;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SSE {

    @GetMapping("/sse")
    public SseEmitter sendSSE() {
        SseEmitter emitter = new SseEmitter();

        Thread thread = new Thread(() -> {
            try {

                for (int i = 0; i < 5; i++) {
                    emitter.send(SseEmitter.event().data("Number" + i));
                    Thread.sleep(1500);
                    if (i == 4) {
                        emitter.complete();
                    }
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        thread.start();

        return emitter;
    }
}
