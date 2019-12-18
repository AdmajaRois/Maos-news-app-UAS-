package com.admaja.maos_aplikasiberita;

public class News {
    private String mTitle;

    private String mSection;

    private String mAuthor;

    private String mDate;

    private String mUrl;

    private String mThumbnail;

    private String mTrailTextHtml;

    /**
     * Constructs a new {@link News} object.
     *
     * @param title is the title of the article
     * @param section is the section name of the article
     * @param author is author name in article
     * @param date is the web publication date of the article
     * @param url is the website URL to find more details about the article
     * @param thumbnail is the thumbnail of the article
     * @param trailText is trail text of the article with string type Html
     */

    public News(String mTitle, String mSection, String mAuthor, String mDate, String mUrl, String mThumbnail, String mTrailTextHtml) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mAuthor = mAuthor;
        this.mDate = mDate;
        this.mUrl = mUrl;
        this.mThumbnail = mThumbnail;
        this.mTrailTextHtml = mTrailTextHtml;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public String getmTrailTextHtml() {
        return mTrailTextHtml;
    }
}
