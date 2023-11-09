package com.kace.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseMenuItemsDTO {
    private String title;
    private String icon;
    private String link;
    private List<ResponseMenuItemsDTO> children;
    private String badgeText;
    private String badgeClass;

    public ResponseMenuItemsDTO(String title, String icon, String link, String badgeText, String badgeClass) {
        this.title = title;
        this.icon = icon;
        this.link = link;
        this.children = new ArrayList<>();
        this.badgeText = badgeText;
        this.badgeClass = badgeClass;
    }

    public void addChild(ResponseMenuItemsDTO child) {
        children.add(child);
    }

}
