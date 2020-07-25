package com.tingge.pojo;

public class Case {
    private String CaseId;
    private String ApiId;
    private String Desc;
    private String Params;
    private String ExpectedResponseData;
    private String ActualResponseData;
    private String PreValidateSql;
    private String PreValidateResult;
    private String AfterValidateSql;
    private String AfterValidateResult;

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public String getApiId() {
        return ApiId;
    }

    public void setApiId(String apiId) {
        ApiId = apiId;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getParams() {
        return Params;
    }

    public void setParams(String params) {
        Params = params;
    }

    public String getExpectedResponseData() {
        return ExpectedResponseData;
    }

    public void setExpectedResponseData(String expectedResponseData) {
        ExpectedResponseData = expectedResponseData;
    }

    public String getActualResponseData() {
        return ActualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        ActualResponseData = actualResponseData;
    }

    public String getPreValidateSql() {
        return PreValidateSql;
    }

    public void setPreValidateSql(String preValidateSql) {
        PreValidateSql = preValidateSql;
    }

    public String getPreValidateResult() {
        return PreValidateResult;
    }

    public void setPreValidateResult(String preValidateResult) {
        PreValidateResult = preValidateResult;
    }

    public String getAfterValidateSql() {
        return AfterValidateSql;
    }

    public void setAfterValidateSql(String afterValidateSql) {
        AfterValidateSql = afterValidateSql;
    }

    public String getAfterValidateResult() {
        return AfterValidateResult;
    }

    public void setAfterValidateResult(String afterValidateResult) {
        AfterValidateResult = afterValidateResult;
    }

    @Override
    public String toString() {
        return "CaseId="+CaseId+",ApiId="+ApiId+",Desc="+Desc+",params="+Params+",ExpectedResponseData="+ExpectedResponseData+",ActualResponseData="+ActualResponseData;
    }
}
