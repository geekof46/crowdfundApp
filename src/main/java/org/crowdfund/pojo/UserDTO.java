package org.crowdfund.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.crowdfund.models.UserRole;

import java.time.Instant;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private String alias;
    private UserRole role;
    private Instant createDate;
    private Integer version;
    private String bankAccountNumber;
    private Instant updateDate;
}
