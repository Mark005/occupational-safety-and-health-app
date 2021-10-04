package com.nncompany.api.model.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseList {
    private List<?> list;
    private Long total;
}
