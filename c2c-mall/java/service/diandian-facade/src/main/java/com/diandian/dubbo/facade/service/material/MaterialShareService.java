package com.diandian.dubbo.facade.service.material;

import com.diandian.dubbo.facade.model.material.MaterialShareModel;

public interface MaterialShareService {

    void save(MaterialShareModel ma);

    MaterialShareModel findShareById(Long merchantId,Long materialId);

}
