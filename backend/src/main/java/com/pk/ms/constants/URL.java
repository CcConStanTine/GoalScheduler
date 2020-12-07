package com.pk.ms.constants;

public enum URL {
    URL1("https://res.cloudinary.com/ccconstantine/image/upload/v1607097618/person_tgl8si.png");

    private final String defaultImageURL;

    URL(String defaultImageURL) {
        this.defaultImageURL = defaultImageURL;
    }

    public String getURL() {
        return defaultImageURL;
    }
}
