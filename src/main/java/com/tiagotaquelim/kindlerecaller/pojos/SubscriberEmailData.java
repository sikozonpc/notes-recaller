package com.tiagotaquelim.kindlerecaller.pojos;

import java.util.List;

public record SubscriberEmailData(
        String email,
        String name,
        List<EmailHighlightField> highlights
) {
}
