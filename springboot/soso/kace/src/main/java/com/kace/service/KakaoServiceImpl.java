package com.kace.service;

import com.kace.dto.MenuAuthDTO;
import com.kace.dto.MenuDTO;
import com.kace.dto.ResponseMenuItemsDTO;
import com.kace.dto.UserDTO;
import com.kace.entity.Menu;
import com.kace.ra.itf.KakaoRAO;
import com.kace.ra.itf.KakaoRAOJPA;
import com.kace.service.itf.KakaoService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KakaoServiceImpl implements KakaoService {
    @Autowired
    KakaoRAO rao;
    @Autowired
    KakaoRAOJPA raojpa;

    @Override
    public List<MenuDTO> testAll() {
        return rao.testAll();
    }

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<ResponseMenuItemsDTO> testAllJPA(Integer userCode) {
        // entity to dto
        List<MenuDTO> menuListDTOS = raojpa.selectMenus(userCode).stream()
                .map(MenuDTO::toDTO)
                .collect(Collectors.toList());
        return mapDataToMenuItems(menuListDTOS);
    }

    @Override
    public List<MenuDTO> findMenus() {
        return raojpa.findAll(Sort.by(Sort.Direction.ASC, "menuCode")).stream()
                .map(MenuDTO::toDTO)
                .collect(Collectors.toList());
    }
    public List<ResponseMenuItemsDTO> mapDataToMenuItems(List<MenuDTO> menuListDTOS) {
        List<ResponseMenuItemsDTO> responseMenuItemDTOS = new ArrayList<>();
        Map<Integer, ResponseMenuItemsDTO> parentMap = new HashMap<>();

        for (MenuDTO menuDTO : menuListDTOS) {
            ResponseMenuItemsDTO menuItem = new ResponseMenuItemsDTO(
                    menuDTO.getMenuTitle(),
                    menuDTO.getMenuIcon(),
                    menuDTO.getMenuLink(),
                    menuDTO.getMenuBadgeText(),
                    menuDTO.getMenuClass()
            );
            if (menuDTO.getParentCode() == null) {
                // 최상단 폴더일 때
                responseMenuItemDTOS.add(menuItem);
                parentMap.put(menuDTO.getMenuCode(), menuItem);
            } else {
                // 자식 항목일 때
                ResponseMenuItemsDTO parentMenu = parentMap.get(menuDTO.getParentCode());
                if (parentMenu != null) {
                    parentMenu.addChild(menuItem);
                }
            }
        }
        return responseMenuItemDTOS;
    }

    @Override
    public String sendMail() {
        MimeMessage message = mailSender.createMimeMessage();
//        SimpleMailMessage message = new SimpleMailMessage();
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            // 1. 메일 발신자 설정
            messageHelper.setFrom("s_msjeon@yulchon.com");
            // 2. 메일 수신자 설정
            messageHelper.setTo("s_msjeon@yulchon.com");
            // 3. 메일 제목 설정
            messageHelper.setSubject("test_subject");
            // 4. 메일 내용 설정
            // HTML 적용됨
            String content = "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>\n" +
                    "<body>  &nbsp;<span style=\"font-family: 맑은 고딕; font-size: 11pt;\">제출용 저장매체 사용신청이 접수되었습니다. 인프라보안팀에서 제출용 저장매체를 수령해 주세요.</span><br>  <table border=\"1\" width=\"100%\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse: collapse; border: 1px solid #e1e1e1; font-family: 맑은 고딕; font-size: 10pt;\">  \t<tr>  \t\t<td width=\"150\" align=\"RIGHT\" bgcolor=\"#DDE1F5\">신청 매체 종류</td>  \t\t<td width=\"*\">&nbsp;USB</td>  \t</tr>  \t<tr>  \t\t<td align=\"RIGHT\" bgcolor=\"#DDE1F5\">데이터 크기</td>  \t\t<td>&nbsp;1GB</td>  \t</tr>  \t<tr>  \t\t<td align=\"RIGHT\" bgcolor=\"#DDE1F5\">사용 목적</td>  \t\t<td>&nbsp;법원제출</td>  \t</tr>  \t<tr>  \t\t<td align=\"RIGHT\" bgcolor=\"#DDE1F5\">매체 수량</td>  \t\t<td>&nbsp;1</td>  \t</tr>  \t<tr>  \t\t<td align=\"RIGHT\" bgcolor=\"#DDE1F5\">관련 MatterCode</td>  \t\t<td>&nbsp;1111가합11111</td>  \t</tr>  \t<tr>  \t\t<td align=\"RIGHT\" bgcolor=\"#DDE1F5\">실제 사용자</td>  \t\t<td>&nbsp;전민수</td>  \t</tr>  \t<tr>  \t\t<td align=\"RIGHT\" bgcolor=\"#DDE1F5\">신청자</td>  \t\t<td>&nbsp;전민수</td>  \t</tr>  </table> </body> </html> ";
            messageHelper.setText(content,true);

            // 4. 메일 전송
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "hi";
    }

    @Override
    public String testSP() {
        System.out.println("rao.testSP().get(0) = " + rao.testSP().get(0).get(0));
        System.out.println("rao.testSP().get(1) = " + rao.testSP().get(1).get(0));
        return "zz";
    }

    @Override
    public void addMenu(MenuDTO menuDTO) {
        // 메뉴 저장
        Menu menu = raojpa.save(Menu.toEntity(menuDTO));

        // 모든 사용자에 추가된 메뉴에 대해 메뉴권한 CRUD(0100)로우 추가 로직
        // 1.모든 사용자 가져온다.
        List<UserDTO> users = findAllUsers();

        // 2. 추가된 메뉴코드를 등록한다 (30, 0,1,0,0, 사용자명, 사용자코드)
        for(UserDTO userDTO : users){
            HashMap<String, String> m = new HashMap<>();
            m.put("MENU_CODE", String.valueOf(menu.getMenuCode()));
            m.put("USER_NAME", userDTO.getAC_LOGINID());
            m.put("USER_CODE", userDTO.getAC_ID());
            rao.registerMenuAuth(m);
        }
    }

    @Override
    public void updateMenu(MenuDTO menuDTO) {
        raojpa.save(Menu.toEntity(menuDTO));
    }

    @Override
    public void deleteMenu(Integer menuCode) {
        raojpa.deleteById(menuCode);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return rao.findAllUsers();
    }

    @Override
    public List<MenuAuthDTO> selectUserMenuAuth(int userCode) {
        return rao.selectUserMenuAuth(userCode);
    }

    @Override
    public void updateMenuAuth(MenuAuthDTO menuAuthDTO) {
        rao.updateMenuAuth(menuAuthDTO);
    }


//    public List<MenuList> testAllJPAA() {
//        // entity가져옴
//        List<MenuListDTO> kakaoDTOS = raojpa.findAll().stream()
//                .map(MenuListDTO::toDTO)
//                .collect(Collectors.toList());
//
//        // dto to entity
//        return kakaoDTOS.stream()
//                .map(MenuList::toEntity)
//                .collect(Collectors.toList());
//    }


}
