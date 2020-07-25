package com.tingge.cases;

import com.tingge.Until.ExcelUntil;
import com.tingge.Until.caseUntil;

import org.testng.annotations.DataProvider;

import java.util.Scanner;

public class LoginCase extends BaseProcess {
//    @Test(dataProvider = "datas")
//    public void test(String caseId,String apiId,String paramters) {
//        //url
//        String url = RestUntil.getUrlByApiId(apiId);
//        //type
//        String type =RestUntil.getTypeByApiId(apiId);
//        //需要的参数
//        Map<String,String> params = (Map<String,String>) JSONObject.parse(paramters);
//        String result = HttpUntil.doService(url, type, params);
//        System.out.println(result);
//        ExcelUntil.writeBackDates.add(new WriteBackDate("login",caseId,"ActualResponseData",result));
//        ExcelUntil.writerBackData("src/main/resources/casesV1.xls","login",caseId,"ActualResponseData",result);
//    }

    @DataProvider
    public Object[][] datas() {
//        String[] cellNames = {"CaseId", "ApiId", "Params", "ExpectedResponseData"}; 写入父类
        Object[][] datas = caseUntil.getCaseDatasByApiId("2", cellNames);
        return datas;

    }

}
