package com.tiagotaquelim.kindlerecaller.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HighlightPojo {
    private String text;
    private boolean isNoteOnly;
    private LocationPojo location;
    private String note;

    public HighlightPojo(String text, boolean isNoteOnly, LocationPojo location, String note) {
        this.text = text;
        this.isNoteOnly = isNoteOnly;
        this.location = location;
        this.note = note;
    }

    public HighlightPojo() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNoteOnly() {
        return isNoteOnly;
    }

    public void setNoteOnly(boolean noteOnly) {
        isNoteOnly = noteOnly;
    }

    public LocationPojo getLocation() {
        return location;
    }

    public void setLocation(LocationPojo location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
