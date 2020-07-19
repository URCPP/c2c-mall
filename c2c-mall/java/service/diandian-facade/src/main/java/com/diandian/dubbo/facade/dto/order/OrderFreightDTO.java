package com.diandian.dubbo.facade.dto.order;

import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderFreightDTO implements Serializable {
    private BigDecimal freight;
    private Long repositoryId;
    private Integer stock;
    private TransportInfoModel transport;

}
