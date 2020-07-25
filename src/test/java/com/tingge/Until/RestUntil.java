package com.tingge.Until;

import com.tingge.pojo.Rest;

import java.util.ArrayList;
import java.util.List;

public class RestUntil {
    public static List<Rest> rests = new ArrayList<Rest>();
    static {
        // 将所有数据解析封装到cases
       List<Rest> list = ExcelUntil.load("src/test/resources/casesV1.xlsx","接口信息",Rest.class);
       rests.addAll(list);
    }

    /** 根据接口编号获取接口地址
    *@Description
    *@Param
    *@Return
    */
    public static String getUrlByApiId(String apiId){
        for (Rest rest : rests) {
            if (rest.getApiId().equals(apiId)){
                return rest.getUrl();
            }
        }
        return "";
    }
    /**
    *@Description 根据接口编号获取接口请求类型
    *@Param
    *@Return
    */
    public static String getTypeByApiId(String apiId) {
        for (Rest rest : rests) {
            if (rest.getApiId().equals(apiId)) {
                return rest.getType();
            }
        }
        return "";
    }
}
