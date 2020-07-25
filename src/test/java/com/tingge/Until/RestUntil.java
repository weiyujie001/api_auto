package com.tingge.Until;

import com.tingge.pojo.Rest;

import java.util.ArrayList;
import java.util.List;

public class RestUntil {
    public static List<Rest> rests = new ArrayList<Rest>();
    static {
        // ���������ݽ�����װ��cases
       List<Rest> list = ExcelUntil.load("src/test/resources/casesV1.xlsx","�ӿ���Ϣ",Rest.class);
       rests.addAll(list);
    }

    /** ���ݽӿڱ�Ż�ȡ�ӿڵ�ַ
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
    *@Description ���ݽӿڱ�Ż�ȡ�ӿ���������
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
