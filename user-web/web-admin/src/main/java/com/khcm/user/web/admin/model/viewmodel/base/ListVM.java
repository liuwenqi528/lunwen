package com.khcm.user.web.admin.model.viewmodel.base;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class ListVM<VM> {
    @NonNull
    private String retCode;

    @NonNull
    private String retInfo;

    private List<VM> list;
}
