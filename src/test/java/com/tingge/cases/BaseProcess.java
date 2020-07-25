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
*@Description �ӿڲ���ͳһ����������
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
            //��֤�ֶ�
            String PreValidateResult= DBCheckUtil.doQuery(PreValidateSql);
            ExcelUntil.writeBackDates.add(new WriteBackDate("login",caseId,"PreValidateResult",PreValidateResult));
        }
        
        //url
        String url = RestUntil.getUrlByApiId(apiId);
        logger.info("�ύ��Url");
        //type
        String type =RestUntil.getTypeByApiId(apiId);
        //�滻�����еı���
        String paramster = VariAbleUntil.replaceVariAbles(paramters);

        //������������MAp
        Map<String,String> params = (Map<String,String>) JSONObject.parse(paramster);
        //����doservice�������õ���Ӧ����
        String result = HttpUntil.doService(url, type, params);
        logger.info("��������"+result);
//        System.out.println(result);
        //���Դ���
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
