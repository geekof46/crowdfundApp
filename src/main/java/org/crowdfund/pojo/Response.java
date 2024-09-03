package org.crowdfund.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class Response {
    private boolean success;
    private Object data;
    private String error;
    private String message;
}
