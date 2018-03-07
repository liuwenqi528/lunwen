package com.khcm.user.web.admin.model.viewmodel.base;

import lombok.*;

import java.util.List;

/**
 * Created by yangwb on 2017/11/28.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class PageVM<VM> {

    @NonNull
    private String retCode;

    @NonNull
    private String retInfo;

    private long total;

    private long count;

    private List<VM> rows;
}
