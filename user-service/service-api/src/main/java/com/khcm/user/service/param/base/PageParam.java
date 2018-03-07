package com.khcm.user.service.param.base;

import lombok.*;

import java.util.Map;

/**
 * 分页信息 DTO
 * <p>
 * 注解 @NoArgsConstructor, @AllArgsConstructor 和 @RequiredArgsConstructor 用在类上，
 * 分别自动生成无参构造器，使用所有参数的构造器，以及把所有 @NonNull 属性作为参数的构造器，如果指定 staticName = "of" 参数，
 * 会生成一个返回对象的静态工厂方法，方法签名为：类名.of(实参列表)，比使用构造函数方便
 *
 * @author wangtao
 * @date 2017/11/22.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
public class PageParam {

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 开始页的第一行
     */
    @NonNull
    private Integer firstRow;

    /**
     * 每页行数
     */
    @NonNull
    private Integer numberOfRows;

    /**
     * 查询过滤器（条件），形式如:(key = 属性名, value = 属性值)
     */
    private Map<String, Object> filters;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方向
     */
    private String direction;

}
