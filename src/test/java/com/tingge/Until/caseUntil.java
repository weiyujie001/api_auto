package com.tingge.Until;


import com.tingge.pojo.Case;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class caseUntil {
    /*
    *  保存所有用例对象
    * */
    public static  List<Case> cases = new ArrayList<Case>();
    static {
        // 将所有数据解析封装到cases
        List<Case> list = ExcelUntil.load("src/test/resources/casesV1.xlsx","login",Case.class);
        cases.addAll(list);
    }
    /**
     *@Description
     *@Param [apiId, cellNames]   apiId 指定接口编号
     *                            cellNames 要获取响应的列名
     *@Return java.lang.Object[][]
     */
    public static Object [] [] getCaseDatasByApiId(String apiId,String [] cellNames){
        Class<Case> clazz = Case.class;
        ArrayList<Case> csList = new ArrayList<Case>();
        // 循环找出接口对应的这些用例数据
        for (Case cs : caseUntil.cases) {
            if (cs.getApiId().equals(apiId)){
                csList.add(cs);
            }
        }
        Object [][] datas = new Object[csList.size()][cellNames.length];

        for (int i = 0; i < csList.size(); i++) {
            Case cs = csList.get(i);
            for (int j = 0; j < cellNames.length; j++) {
             /*   String CellName = cellNames[j];
                String value = "";
                if (cellNames.equals("CaseId")){
                    value =cs.getCaseId();
                }else if (cellNames.equals("ApiId")){
                    value =cs.getApiId();
                }else if (cellNames.equals("Desc")){
                    value =cs.getDesc();
                }else if (cellNames.equals("Params")){
                    value =cs.getParams();
                }*/
                //要反射的方法名
                String methodName = "get"+cellNames[j];
                try {
                    Method method = clazz.getMethod(methodName);
                    String value =(String) method.invoke(cs);
                    datas [i][j] = value;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return datas;
    }

    public static void main(String[] args) {
        String [] cellNames ={"Params"};
        Object[][] datas = getCaseDatasByApiId("1", cellNames);
        for (Object[] objects : datas) {
            for (Object object : objects) {
                System.out.println(object);
            }
        }
    }

}
