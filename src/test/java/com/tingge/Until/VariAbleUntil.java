package com.tingge.Until;

import com.tingge.pojo.Variable;
import com.tingge.pojo.WriteBackDate;


import java.lang.reflect.Method;
import java.util.*;

public class VariAbleUntil {
    public static Map<String,String> varibleNameAndValueMap = new HashMap<String, String>();
    public static List<Variable> variableList = new ArrayList<Variable>();
    static {
        List<Variable> list = ExcelUntil.load("src/test/resources/casesV1.xlsx","����",Variable.class);
        variableList.addAll(list);
        //��������������ֵ�ŵ�Map
        loadVaribleToMap();
        //��Ҫ��д�����ݣ���ȡ��������������
//        ExcelUntil.loadRownnumAndCellnumMapping(PropertiesUntil.getExcelPath(),"����");
    }

    private static void loadVaribleToMap() {
        for (Variable variable : variableList) {
            String variableName = variable.getName();
            //��ȡ����ֵ
            String variableValue = variable.getValue();
            //��ȡ�����������
//            if (variableValue==null || variableValue.trim().length()==0){
//                String reflectclass = variable.getReflectclass();
//                String reflectMethof = variable.getReflectMethof();
//                //��ȡ��̬����
//                try {
//                    //ͨ�������ȡ���ͻ�ȡ�ֽ���clazz
//                    Class clazz = Class.forName(reflectclass);
//                    //ͨ�����䴴������
//                    Object o = clazz.newInstance();
//                    //ͨ�������ȡҪ���õķ�������
//                    Method method = clazz.getMethod(reflectMethof);
//                    //������÷�������ȡ�����ķ���ֵ
//                    variableValue = (String) method.invoke(o);
//                    //�����ȡ��ֵ
//                    ExcelUntil.writeBackDates.add(new WriteBackDate("����",variableName,"ReflectValue",variableValue));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            varibleNameAndValueMap.put(variableName,variableValue);
        }
    }

    /**
    *@Description �滻����
    *@Param
    *@Return 
    */

    public static String replaceVariAbles(String paramters) {

        Set<String> varbleNames = varibleNameAndValueMap.keySet();
        for (String varbleName : varbleNames) {
            //�ж�������ֱ��������滻��
            if (paramters.contains(varbleName)){
                paramters = paramters.replace(varbleName,varibleNameAndValueMap.get(varbleName));
            }
        }
        return paramters;

    }
}
