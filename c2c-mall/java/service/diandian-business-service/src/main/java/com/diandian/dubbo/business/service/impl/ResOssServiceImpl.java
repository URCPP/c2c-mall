package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandian.dubbo.business.common.constant.ResConstant;
import com.diandian.dubbo.business.mapper.ResOssMapper;
import com.diandian.dubbo.common.oss.AliyunStorageUtil;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.res.MoveGroupDTO;
import com.diandian.dubbo.facade.model.res.ResOssModel;
import com.diandian.dubbo.facade.service.res.ResOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 资源对象存储
 *
 * @author zzhihong
 * @date 2019/02/19
 */
@Service("resOssService")
@Slf4j
public class ResOssServiceImpl  implements ResOssService {

    @Autowired
    private ResOssMapper resOssMapper;

    @Override
    public String uploadOneFile(String urlData) {
        return AliyunStorageUtil.uploadBase64Img("diandian-business", urlData);
    }

    @Override
    public List<String> upload(List<Map<String,String>> list) {
        try {
            ArrayList<String> imageUrls = new ArrayList<>();
            list.forEach(el->{
                String name = el.get("name");
                String urlData=el.get("urlData");
                String groupId=el.get("groupId");
                String suffix = urlData.split(";")[0];
                String imagePattern = "data:image/(jpg|png|jpeg)";
                String resType="";
                if(Pattern.matches(imagePattern, suffix)){
                    resType= ResConstant.IMAGE;
                }else{
                    throw new DubboException("素材格式错误");
                }
                String imageUrl = AliyunStorageUtil.uploadBase64Img("diandian-business", urlData);
                ResOssModel resOssModel = new ResOssModel();
                resOssModel.setResType(resType);
                resOssModel.setAliasName(name);
                resOssModel.setObjUrl(imageUrl);
                resOssModel.setGroupId(Long.valueOf(groupId));
                resOssMapper.insert(resOssModel);
                imageUrls.add(imageUrl);
            });
            return imageUrls;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传素材失败", e);
            throw new DubboException("上传失败");
        }
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String groupId=(String)params.get("groupId");
        String resType=(String)params.get("resType");
        String keywords=(String)params.get("keywords");
        IPage<ResOssModel> resOssModelIPage = resOssMapper.selectPage(
                new PageWrapper<ResOssModel>(params).getPage(),
                new QueryWrapper<ResOssModel>()
                        .eq("res_type",resType)
                        .eq(Long.valueOf(groupId)!=0,"group_id",groupId)
                        .like(StringUtils.isNotEmpty(keywords),"alias_name",keywords)
                        .orderByDesc("id")
        );
        return new PageResult(resOssModelIPage);
    }

    @Override
    public void updateById(ResOssModel resOssModel) {
        resOssMapper.updateById(resOssModel);
    }

    @Override
    public void deleteById(List<Long> ids) {
        resOssMapper.deleteBatchIds(ids);
    }

    @Override
    public void moveGroup(MoveGroupDTO moveGroupDTO) {
        Long targetGroup=moveGroupDTO.getTargetGroup();
        List<Long> ids=moveGroupDTO.getIds();
        ids.forEach(id->{
            ResOssModel resOssModel = new ResOssModel();
            resOssModel.setId(Long.valueOf(id));
            resOssModel.setGroupId(Long.valueOf(targetGroup));
            resOssMapper.updateById(resOssModel);
        });
    }
}
