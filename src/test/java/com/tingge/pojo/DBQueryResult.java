package com.tingge.pojo;

import java.util.Map;

public class DBQueryResult {
    /**
    *@Description 脚本编号
    *@Param
    *@Return
    */
    private String no;
    //脚本执行查到数据，保存到map中（key是字段名，value是数据
    private Map<String,Object> columenLabelAndValues;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }


    public Map<String, Object> getColumenLabelAndValues() {
        return columenLabelAndValues;
    }

    public void setColumenLabelAndValues(Map<String, Object> columenLabelAndValues) {
        this.columenLabelAndValues = columenLabelAndValues;
    }
}
