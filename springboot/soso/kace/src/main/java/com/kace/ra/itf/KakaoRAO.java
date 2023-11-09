package com.kace.ra.itf;

import com.kace.dto.MenuAuthDTO;
import com.kace.dto.MenuDTO;
import com.kace.dto.UserDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface KakaoRAO {

    public List<MenuDTO> testAll();
    public List<List<HashMap<String, String>>> testSP();

    public List<UserDTO> findAllUsers();

    public void registerMenuAuth(HashMap hashMap);

    public List<MenuAuthDTO> selectUserMenuAuth(int userCode);

    public void updateMenuAuth(MenuAuthDTO menuAuthDTO);

}
