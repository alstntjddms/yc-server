package com.kace.entity;

import com.kace.dto.MenuDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MENU_LIST")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;
    private String menuTitle;
    private String menuIcon;
    private String menuLink;
    private String menuBadgeText;
    private String menuClass;
    private Integer parentCode;

    public static Menu toEntity(MenuDTO menuListDTO){
        return Menu.builder()
                .menuCode(menuListDTO.getMenuCode())
                .menuTitle(menuListDTO.getMenuTitle())
                .menuIcon(menuListDTO.getMenuIcon())
                .menuLink(menuListDTO.getMenuLink())
                .menuBadgeText(menuListDTO.getMenuBadgeText())
                .menuClass(menuListDTO.getMenuClass())
                .parentCode(menuListDTO.getParentCode())
                .build();
    }
}
