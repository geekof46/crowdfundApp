package org.crowdfund.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
@AllArgsConstructor
public class UserCreateRequest {
    private String emailId;
    private String name;
    private String phoneNumber;
}
