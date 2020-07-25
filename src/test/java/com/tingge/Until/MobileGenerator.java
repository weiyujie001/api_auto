package com.tingge.Until;

import java.util.Map;

public class MobileGenerator {
    /**
    *@Description 生成注册的手机号随机手机号
    *@Param
    *@Return
    */
    public String getMobileGenerator(){
        String sql = "select concat(max(mobilephone)+1,'') as RegisterMobile from member";
        Map<String, Object> queryValue = JDBCutil.query(sql);
        return (String) queryValue.get("RegisterMobile");

    }
    /**
    *@Description 生产系统还未注册的手机号
    *@Param
    *@Return
    */
    public String generatorSystemNotExistMobile(){
        String sql = "select concat(max(mobilephone)+2,'') as SystemNotExistMobile from member";
        Map<String, Object> queryValue = JDBCutil.query(sql);
        return (String) queryValue.get("SystemNotExistMobile");


    }

    public static void main(String[] args) {
        MobileGenerator mobileGenerator = new MobileGenerator();
        String mobileGenerator1 = mobileGenerator.getMobileGenerator();
        System.out.println(mobileGenerator1);
    }
}
