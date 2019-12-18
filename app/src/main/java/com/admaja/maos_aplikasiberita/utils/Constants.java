package com.admaja.maos_aplikasiberita.utils;

public class Constants {

    private Constants() {
    }

    static  final String JSON_KEY_RESPONSE = "response";
    static final String JSON_KEY_RESULT = "results";
    static final  String JSON_KEY_WEB_TITLE = "webTitle";
    static final  String JSON_KEY_SECTION_NAME ="sectionName";
    static final  String JSON_KEY_WEB_PUBLICATION_DATE = "webpublicationDate";
    static final  String JSON_KEY_WEB_URl = "webUrl";
    static final  String JSON_KEY_TAGS = "tags";
    static final String JSON_KEY_FIELDS = "fields";
    static final  String JSON_KEY_THUMBNAIL = "thumbnail";
    static final String JSON_KEY_TRAIL_TEXT = "trailText";

    static  final int RAD_TIMEOUT =10000;

    static final int CONNECT_TIMEOUT = 15000;

    static final int SUCCESS_RESPONSE_CODE = 200;

    static final String REQUEST_METHHOD_GET = "GET";

    public static final String NEWS_REQUEST_URL = "https://newsapi.org/s/indonesia-news-api";

    public static final String QUERY_PARAM = "q";
    public static final String OREDER_BY_PARAM = "order-by";
    public static final String PAGE_SIZE_PARAM = "page-size";
    public static final String ORDER_DATE_PARAM = "order-date";
    public static final String FROM_DATE_PARAM = "form-date";
    public static final String SHOW_FIELDS_PARAM = "show-fields";
    public static final String FORMAT_PARAM = "format";
    public static final String SHOW_TAGS_PARAM = "show-tags";
    public static final String API_KEY_PARAM = "api-key";
    public static final String SECTION_PARAM = "section";

    public static final String SHOW_FIELDS = "thumnail,trailText";

    public static final  String FORMAT = "json";

    public static final String SHOW_TAGS = "contributor";

    public static final String API_KEY = "59309fa6619942b8ba20256bb34b0e0a";

    public static final int DEFAULT_NUMBER = 0;

    public static final int HEADLINE = 0;
    public static final int BUSINESS = 1;
    public static final int ENTERTAIMENT = 2;
    public static final int HEALTH = 3;
    public static final int SICENCE = 4;
    public static final int SPORTS = 5;
    public static final int TECHNOLOGY = 6;
}
