package org.crowdfund.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String emailId;
    private String name;
    private String phoneNumber;
    private String bankAccountNumber;
    private Integer version;
    private Instant createDate;
    private Instant updateDate;
}
