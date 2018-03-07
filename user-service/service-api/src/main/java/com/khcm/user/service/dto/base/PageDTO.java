package com.khcm.user.service.dto.base;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果 DTO
 *
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@ToString
public class PageDTO<DTO> implements Serializable {

    @NonNull
    private Long totalCount;

    @NonNull
    private List<DTO> content;
}
