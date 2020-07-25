package com.tingge.cases;

import com.alibaba.fastjson.JSONObject;
import com.tingge.Until.*;
import com.tingge.pojo.WriteBackDate;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Properties;


/**
*@Description 接口测试统一处理数据类
*@Param
*@Return 
*/
public class BaseProcess {
    public Logger logger=Logger.getLogger(BaseProcess.class);
    public  String[] cellNames = {"CaseId", "ApiId", "Params", "ExpectedResponseData","PreValidateSql","AfterValidateSql"};
    @Test(dataProvider = "datas")
    public void test1(String caseId,String apiId,String paramters,String ExpectedResponseData,String PreValidateSql,
                      String AfterValidateSql) {
        if (PreValidateSql!=null && PreValidateSql.trim().length()>0){
            PreValidateSql = VariAbleUntil.replaceVariAbles(PreValidateSql);
            //验证字段
            String PreValidateResult= DBCheckUtil.doQuery(PreValidateSql);
            ExcelUntil.writeBackDates.add(new WriteBackDate("login",caseId,"PreValidateResult",PreValidateResult));
        }
        
        //url
        String url = RestUntil.getUrlByApiId(apiId);
        logger.info("提交的Url");
        //type
        String type =RestUntil.getTypeByApiId(apiId);
        //替换参数中的变量
        String paramster = VariAbleUntil.replaceVariAbles(paramters);

        //将参数解析到MAp
        Map<String,String> params = (Map<String,String>) JSONObject.parse(paramster);
        //调用doservice方法，拿到响应报文
        String result = HttpUntil.doService(url, type, params);
        logger.info("请求结果是"+result);
//        System.out.println(result);
        //断言处理，
        if (ExpectedResponseData==null ||ExpectedResponseData.trim().length()==0){
            ExcelUntil.writeBackDates.add(new WriteBackDate("login",caseId,"ActualResponseData",result));

        }else{
            String  actualResult = AssertUntil.assertEquals(result,ExpectedResponseData);
            ExcelUntil.writeBackDates.add(new WriteBackDate("login",caseId,"ActualResponseData",actualResult));
        }

        if (AfterValidateSql!=null && AfterValidateSql.trim().length()>0){
            AfterValidateSql = VariAbleUntil.replaceVariAbles(AfterValidateSql);
            String afterVladidateResult= DBCheckUtil.doQuery(AfterValidateSql);
            ExcelUntil.writeBackDates.add(new WriteBackDate("login",caseId,"AfterValidateResult",afterVladidateResult));
        }
//



    }
    @AfterSuite
    public void batchWriteBackDatas(){
        ExcelUntil.batchWriteBackDatas(PropertiesUntil.getExcelPath());
    }
}
