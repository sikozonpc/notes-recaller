package com.tiagotaquelim.kindlerecaller.subscriber;

import com.tiagotaquelim.kindlerecaller.highlight.Highlight;
import com.tiagotaquelim.kindlerecaller.highlight.HighlightRepository;
import com.tiagotaquelim.kindlerecaller.mail.SendGridService;
import com.tiagotaquelim.kindlerecaller.pojos.EmailHighlightField;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final HighlightRepository highlightRepository;
    private final SendGridService sendGridService;
    private final SubscriberService subscriberService;

    public SubscriberService(SubscriberRepository subscriberRepository, HighlightRepository highlightRepository, SendGridService sendGridService, SubscriberService subscriberService) {
        this.subscriberRepository = subscriberRepository;
        this.highlightRepository = highlightRepository;
        this.sendGridService = sendGridService;
        this.subscriberService = subscriberService;
    }

    public void addNewSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public void sendRandomHighlightsEmailToSubscribers() throws IOException {
        List<Highlight> highlights = highlightRepository.findRandomHighlights(3);

        List<EmailHighlightField> emailFields = highlights.stream()
                .map(highlight -> new EmailHighlightField(
                        highlight.getText(),
                        highlight.getBook().getTitle() + " - " + highlight.getBook().getAuthor(),
                        highlight.getNote()
                ))
                .toList();

        List<Subscriber> subscribers = subscriberService.getAllSubscribers();

        sendGridService.sendTextEmail(subscribers, emailFields);
    }
}
