package com.diandian.dubbo.facade.common.util;

import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import com.diandian.dubbo.facade.vo.OrgListVO;

import java.util.List;

/**
 * 结构树工具类
 * @author cjunyuan
 * @date 2019/2/27 13:44
 */
public class TreeUtil {


    /**
     * 构建部门树
     *
     * @param resultList 结果
     * @param sourceList  源
     * @param isChildren  是否进行子元素递归
     * @return
     */
    public static List<OrgListVO> buildDeptTree(List<OrgListVO> resultList, List<OrgListVO> sourceList, boolean isChildren, int sourceSize) {
        for (int i = 0;i < resultList.size();i++){
            OrgListVO res = resultList.get(i);
            for (int j = 0;j < sourceList.size();j++){
                OrgListVO source = sourceList.get(j);
                if(res.getId().equals(source.getParentId())){
                    res.add(source);
                    sourceList.remove(j);
                    j--;
                } else if(res.getParentId().equals(source.getId())){
                    source.add(res);
                    res = sourceList.remove(j);
                    j--;
                    resultList.set(i, res);
                }
            }
            if(res.getChildren() != null && res.getChildren().size() > 0){
                buildDeptTree(res.getChildren(), sourceList, true, sourceList.size());
            }
        }
        //如果源List数量没有减少则目标List和源List没有交集
        if(sourceList.size() > 0 && sourceSize != sourceList.size()){
            buildDeptTree(resultList, sourceList, isChildren, sourceList.size());
        } else if (!isChildren){
            if(sourceList.size() > 0){
                resultList.add(sourceList.remove(0));
                buildDeptTree(resultList, sourceList, isChildren, sourceList.size());
            }
        }
        return resultList;
    }

    /**
     * 构建部门树
     *
     * @param resultList 结果
     * @param sourceList  源
     * @param isChildren  是否进行子元素递归
     * @return
     */
    public static List<IntactTreeVO> buildIntactTree(List<IntactTreeVO> resultList, List<IntactTreeVO> sourceList, boolean isChildren, int sourceSize) {
        for (int i = 0;i < resultList.size();i++){
            IntactTreeVO res = resultList.get(i);
            for (int j = 0;j < sourceList.size();j++){
                IntactTreeVO source = sourceList.get(j);
                if(res.getId().equals(source.getParentId())){
                    res.add(source);
                    sourceList.remove(j);
                } else if(res.getParentId().equals(source.getId())){
                    source.add(res);
                    res = sourceList.remove(j);
                    resultList.set(i, res);
                }
            }
            if(res.getChildren() != null && res.getChildren().size() > 0){
                buildIntactTree(res.getChildren(), sourceList, true, sourceList.size());
            }
        }
        //如果源List数量没有减少则目标List和源List没有交集
        if(sourceList.size() > 0 && sourceSize != sourceList.size()){
            buildIntactTree(resultList, sourceList, isChildren, sourceList.size());
        } else if (!isChildren){
            if(sourceList.size() > 0){
                resultList.add(sourceList.remove(0));
                buildIntactTree(resultList, sourceList, isChildren, sourceList.size());
            }
        }
        return resultList;
    }
}
