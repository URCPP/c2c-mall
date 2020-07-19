package com.diandian.dubbo.business.service.impl.material;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.material.MaterialShareMapper;
import com.diandian.dubbo.facade.model.material.MaterialShareModel;
import com.diandian.dubbo.facade.service.material.MaterialShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("materialShareService")
@Slf4j
public class MaterialShareServiceImpl implements MaterialShareService {
    @Autowired
    private MaterialShareMapper materialShareMapper;

    @Override
    public void save(MaterialShareModel ma) {
        materialShareMapper.insert(ma);
    }

    @Override
    public MaterialShareModel findShareById(Long merchantId, Long materialId) {
        QueryWrapper<MaterialShareModel> qw=new QueryWrapper<>();
        qw.eq("merchant_id",merchantId).eq("material_id",materialId);
        return materialShareMapper.selectOne(qw);
    }
}
