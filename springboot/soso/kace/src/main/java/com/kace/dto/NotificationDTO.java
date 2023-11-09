package com.kace.dto;


import lombok.Data;

@Data
public class NotificationDTO {

    private final String title;
    private final String body;
    private final String image;
    private final String url;


    private NotificationDTO(String title, String body, String image, String url) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.url = url;
    }

}
