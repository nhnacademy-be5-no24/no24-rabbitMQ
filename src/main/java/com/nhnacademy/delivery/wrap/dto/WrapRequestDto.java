package com.nhnacademy.delivery.wrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WrapRequestDto {
    private String wrapName;
    private Long wrapCost;
}