package org.chainoptim.shared.sanitization;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class SanitizationService {

    private final Safelist safelist;

    public SanitizationService() {
        this.safelist = Safelist.none();

        this.safelist.addTags("b", "i", "u", "strong", "em", "sub", "sup", "blockquote", "ul", "ol", "li");

        this.safelist.addAttributes("a", "href");
    }

    public String sanitize(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, this.safelist);
    }

}
