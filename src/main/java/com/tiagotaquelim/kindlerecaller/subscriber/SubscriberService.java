package com.tiagotaquelim.kindlerecaller.subscriber;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public void addNewSubscriber(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }
}
