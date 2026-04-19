package com.example.SIT305_51C;

/**
 * Immutable Data Model for News Items.
 * Using 'final' ensures the data remains consistent after initialization.
 */
public class NewsItem {
    private final int id;
    private final String title;
    private final String desc;
    private final String source;
    private final int imageResId;
    private final String category;

    public NewsItem(int id, String title, String desc, String source, int imageResId, String category) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.source = source;
        this.imageResId = imageResId;
        this.category = category;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getSource() { return source; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
}