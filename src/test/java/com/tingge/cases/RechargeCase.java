package com.tingge.cases;

import com.tingge.Until.caseUntil;
import org.testng.annotations.DataProvider;

public class RechargeCase extends BaseProcess {
    @DataProvider
    public Object[][] datas() {
//        String [] cellNames = {"CaseId","ApiId","Params"};
        Object[][] datas = caseUntil.getCaseDatasByApiId("2",cellNames);
        return datas;
    }

}
