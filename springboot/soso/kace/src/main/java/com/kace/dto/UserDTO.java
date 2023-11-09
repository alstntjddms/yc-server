package com.kace.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    String AC_ID;
    String AC_LOGINID;
    String AC_PASSWORD;
    Date AC_REGDATE;
    Date AC_UPDDATE;
    Boolean AC_DELYN;
}
