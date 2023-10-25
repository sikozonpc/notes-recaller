package com.tiagotaquelim.kindlerecaller.subscriber;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("api/v1/subscribers")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/send-email")
    public void mailSubscribers() throws IOException {
        subscriberService.sendRandomHighlightsEmailToSubscribers();
    }
}
