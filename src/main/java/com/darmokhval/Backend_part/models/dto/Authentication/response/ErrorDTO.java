package com.darmokhval.Backend_part.models.dto.Authentication.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private Long timestamp;
    private String details;
}
