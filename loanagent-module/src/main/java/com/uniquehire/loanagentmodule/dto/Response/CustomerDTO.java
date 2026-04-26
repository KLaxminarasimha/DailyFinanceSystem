package com.uniquehire.loanagentmodule.dto.Response;

import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;
    private Long authUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
