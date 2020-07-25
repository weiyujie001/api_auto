package com.tingge.Until;

import com.tingge.pojo.Variable;
import com.tingge.pojo.WriteBackDate;


import java.lang.reflect.Method;
import java.util.*;

public class VariAbleUntil {
    public static Map<String,String> varibleNameAndValueMap = new HashMap<String, String>();
    public static List<Variable> variableList = new ArrayList<Variable>();
    static {
        List<Variable> list = ExcelUntil.load("src/test/resources/casesV1.xlsx","变量",Variable.class);
        variableList.addAll(list);
        //将变量及变量的值放到Map
        loadVaribleToMap();
        //需要会写的数据，获取行索引，列索引
//        ExcelUntil.loadRownnumAndCellnumMapping(PropertiesUntil.getExcelPath(),"变量");
    }

    private static void loadVaribleToMap() {
        for (Variable variable : variableList) {
            String variableName = variable.getName();
            //获取变量值
            String variableValue = variable.getValue();
            //获取用例反射变量
//            if (variableValue==null || variableValue.trim().length()==0){
//                String reflectclass = variable.getReflectclass();
//                String reflectMethof = variable.getReflectMethof();
//                //获取动态数据
//                try {
//                    //通过反射获取类型获取字节码clazz
//                    Class clazz = Class.forName(reflectclass);
//                    //通过反射创建对象
//                    Object o = clazz.newInstance();
//                    //通过反射获取要调用的方法对象
//                    Method method = clazz.getMethod(reflectMethof);
//                    //反射调用方法，获取方法的返回值
//                    variableValue = (String) method.invoke(o);
//                    //保存获取的值
//                    ExcelUntil.writeBackDates.add(new WriteBackDate("变量",variableName,"ReflectValue",variableValue));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            varibleNameAndValueMap.put(variableName,variableValue);
        }
    }

    /**
    *@Description 替换数据
    *@Param
    *@Return 
    */

    public static String replaceVariAbles(String paramters) {

        Set<String> varbleNames = varibleNameAndValueMap.keySet();
        for (String varbleName : varbleNames) {
            //判断如果出现变量名，替换掉
            if (paramters.contains(varbleName)){
                paramters = paramters.replace(varbleName,varibleNameAndValueMap.get(varbleName));
            }
        }
        return paramters;

    }
}
