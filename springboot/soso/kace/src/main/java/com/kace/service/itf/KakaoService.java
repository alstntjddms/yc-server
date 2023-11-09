package com.kace.service.itf;

import com.kace.dto.*;

import java.util.List;

public interface KakaoService {
    public List<MenuDTO> testAll();
    public List<ResponseMenuItemsDTO> testAllJPA(Integer userCode);

    public List<MenuDTO> findMenus();

    public String sendMail();
    public String testSP();

    public void addMenu(MenuDTO menuDTO);
    public void updateMenu(MenuDTO menuDTO);
    public void deleteMenu(Integer menuCode);

    public List<UserDTO> findAllUsers();
    public List<MenuAuthDTO> selectUserMenuAuth(int userCode);
    public void updateMenuAuth(MenuAuthDTO menuAuthDTO);
}
