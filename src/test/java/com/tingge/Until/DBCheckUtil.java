package com.tingge.Until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tingge.pojo.DBCheck;
import com.tingge.pojo.DBQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBCheckUtil {
     /**
    *@Description
    *@Param [ValidateSql]
     * * *@Return java.lang.String
     */
    public static String doQuery(String ValidateSql) {

        List<DBCheck> dbChecks = JSONObject.parseArray(ValidateSql, DBCheck.class);
        List<DBQueryResult> dbQueryResults = new ArrayList<DBQueryResult>();
        for (DBCheck dbCheck : dbChecks) {
            //拿到Sql编号
            String no = dbCheck.getNo();
            String sql = dbCheck.getSql();
            //获取查询，获取结果
            Map<String, Object> columenLabelAndValues = JDBCutil.query(sql);
            DBQueryResult dbQueryResult = new DBQueryResult();
            dbQueryResult.setNo(no);
            dbQueryResult.setColumenLabelAndValues(columenLabelAndValues);
            dbQueryResults.add(dbQueryResult);


        }
        return JSONObject.toJSONString(dbQueryResults);

    }



}
