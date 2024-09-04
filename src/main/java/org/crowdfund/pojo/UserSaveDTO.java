package org.crowdfund.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * The type User save dto.
 */
@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDTO {
    @NonNull
    private String userId;
    @NonNull
    private String emailId;
    private String name;
    private String phoneNumber;
}
