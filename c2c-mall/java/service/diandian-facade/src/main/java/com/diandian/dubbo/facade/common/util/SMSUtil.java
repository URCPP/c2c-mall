package com.diandian.dubbo.facade.common.util;

import cn.hutool.core.util.StrUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author zzhihong
 * @date 2018-10-11
 */
public class SMSUtil {

    private static final String accessKeyId="LTAIdeDlWgKksfjD";
    private static final String accessSecret="KAZppzEBkFo9OiK4TpZXEfl7jlsqAs";
    private static final String domain="dysmsapi.aliyuncs.com";
    private static final String regionId="cn-hangzhou";
    private static final String signName="惠享到家";
    private static final String templateCode="SMS_172815160";

    /**
     * 短信验证码发送
     *
     * @param code   验证码
     * @param phones 手机号码 多个 , 隔开
     * @return 成功true
     */
    public static boolean sendMsgValidateCode(String code, String phones) {
        AssertUtil.isTrue(StrUtil.isNotBlank(code), "code不能为空");
        AssertUtil.isTrue(StrUtil.isNotBlank(phones), "phones不能为空");
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phones);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam","{\"code\":"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) {
        boolean b = sendMsgValidateCode("666666", "17891868803");
        System.out.println(b);
    }
}
