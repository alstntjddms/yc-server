package com.kace.ra.itf;

import com.kace.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KakaoRAOJPA extends JpaRepository<Menu, Integer> {
    public List<Menu> findByParentCode(Integer i);

    @Query(value = "" +
            "SELECT * " +
            "FROM MENU_LIST " +
            "WHERE menuCode IN " +
            "(SELECT MENU_CODE FROM MENU_AUTH a WHERE a.USER_CODE = :userCode AND a.R = 1)" +
            "ORDER BY menuCode", nativeQuery = true)
    public List<Menu> selectMenus(Integer userCode);


}
