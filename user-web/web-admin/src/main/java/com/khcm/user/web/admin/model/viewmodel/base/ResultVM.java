package com.khcm.user.web.admin.model.viewmodel.base;

import lombok.*;

/**
 * Created by yangwb on 2017/11/28.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class ResultVM {

    @NonNull
    private String retCode;

    @NonNull
    private String retInfo;

}
