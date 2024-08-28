package org.crowdfund.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @NonNull
    private String userId;
    @NonNull
    private String name;
    private String alias;
    @NonNull
    private String role;
}
