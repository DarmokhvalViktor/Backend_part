package com.darmokhval.Backend_part.model.dto.Authentication.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private Long timestamp;
    private String details;
}
