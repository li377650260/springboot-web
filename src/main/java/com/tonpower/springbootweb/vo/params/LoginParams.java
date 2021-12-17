package com.tonpower.springbootweb.vo.params;

import lombok.Data;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/21 18:25
 */
@Data
public class LoginParams {

    private String account;

    private String password;

    private String nickname;
}
