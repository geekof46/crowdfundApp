package org.crowdfund.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * The type User create request.
 */
@ToString
@Builder
@Getter
@AllArgsConstructor
public class UserCreateRequest {
    private String emailId;
    private String name;
    private String phoneNumber;
}
