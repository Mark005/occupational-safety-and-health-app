package com.nncompany.api.model.wrappers;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseList {
    private List<?> list;
    private Long total;
}
