package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品的其它信息（仓库、库存、运输方式、运输费用计算）
 * @author cjunyuan
 * @date 2019/4/25 13:52
 */
@Getter
@Setter
@ToString
public class RepositoryDetailVO implements Serializable {

    private static final long serialVersionUID = -3005073682453047238L;

    private Long repositoryId;

    private String repositoryName;

    private String address;

    private String contactName;

    private String contactPhone;

    private Integer stock;

    List<TransportInfoVO> transports;

    public void addTransport(TransportInfoVO vo){
        if(null == transports){
            transports = new ArrayList<>();
        }
        transports.add(vo);
    }
}
