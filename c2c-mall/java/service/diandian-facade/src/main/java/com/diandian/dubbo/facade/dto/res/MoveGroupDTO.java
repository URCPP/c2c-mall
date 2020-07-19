package com.diandian.dubbo.facade.dto.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class MoveGroupDTO implements Serializable {
    private Long targetGroup;
    private List<Long> ids;
}
