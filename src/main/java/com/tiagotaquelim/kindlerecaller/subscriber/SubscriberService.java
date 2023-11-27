package com.tiagotaquelim.kindlerecaller.subscriber;

import com.tiagotaquelim.kindlerecaller.highlight.Highlight;
import com.tiagotaquelim.kindlerecaller.highlight.HighlightRepository;
import com.tiagotaquelim.kindlerecaller.mail.HighlightsEmailTemplate;
import com.tiagotaquelim.kindlerecaller.mail.WelcomeEmailTemplate;
import com.tiagotaquelim.kindlerecaller.pojos.EmailHighlightField;
import com.tiagotaquelim.kindlerecaller.pojos.SubscriberEmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final HighlightRepository highlightRepository;
    private final HighlightsEmailTemplate highlightsEmailTemplate;
    private final WelcomeEmailTemplate welcomeEmailTemplate;

    @Autowired
    public SubscriberService(
            SubscriberRepository subscriberRepository,
            HighlightRepository highlightRepository,
            HighlightsEmailTemplate highlightsEmailTemplate,
            WelcomeEmailTemplate welcomeEmailTemplate
    ) {
        this.subscriberRepository = subscriberRepository;
        this.highlightRepository = highlightRepository;
        this.highlightsEmailTemplate = highlightsEmailTemplate;
        this.welcomeEmailTemplate = welcomeEmailTemplate;
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public void sendRandomHighlightsEmailToSubscribers(Number numOfHighlights) {
        List<SubscriberEmailData> subscriberEmailData = this
                .getAllSubscribers()
                .stream()
                .map(subscriber -> {
                    List<Highlight> randomHighlights = highlightRepository.findRandomHighlights(subscriber.getId(), numOfHighlights);

                    List<EmailHighlightField> highlights = randomHighlights.stream()
                            .map(highlight -> new EmailHighlightField(
                                    highlight.getText(),
                                    highlight.getBook().getTitle() + " - " + highlight.getBook().getAuthor(),
                                    highlight.getNote()
                            ))
                            .toList();

                    return new SubscriberEmailData(
                            subscriber.getEmail(),
                            subscriber.getName(),
                            highlights
                    );
                })
                .toList();

        this.highlightsEmailTemplate.composeAndSend(subscriberEmailData);
    }

    public void createAndMailSubscriber(Subscriber payload) throws IOException {
        Subscriber subscriber = subscriberRepository.save(payload);

        this.welcomeEmailTemplate.composeAndSend(subscriber);
    }
}
