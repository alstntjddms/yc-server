package com.kace.dto;

import com.kace.entity.Menu;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDTO {
    private int menuCode;
    private String menuTitle;
    private String menuIcon;
    private String menuLink;
    private String menuBadgeText;
    private String menuClass;
    private Integer parentCode;


    public static MenuDTO toDTO(Menu menuList){
        return MenuDTO.builder()
                .menuCode(menuList.getMenuCode())
                .menuTitle(menuList.getMenuTitle())
                .menuIcon(menuList.getMenuIcon())
                .menuLink(menuList.getMenuLink())
                .menuBadgeText(menuList.getMenuBadgeText())
                .menuClass(menuList.getMenuClass())
                .parentCode(menuList.getParentCode())
                .build();
    }
}
