package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class APODStringExtraction {
    String url;
    String explanation;
    String title;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setTitle(String title) {this.title = title; }

    public String getUrl() {
        return url;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTitle() { return title; }
}
