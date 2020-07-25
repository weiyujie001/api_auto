package com.tingge.Until;

import org.testng.Assert;

public class AssertUntil {
    /**
    *@Description 自定义类库 断言实际测试结果是否一样
    *@Param result:实际结果  Expectresult:期望结果
    *@Return
    */
    public static String assertEquals(String actualResult,String Expectresult ){
        String result = "通过";
        try{
            Assert.assertEquals(actualResult,Expectresult);
        }catch (Throwable e){
            result = actualResult;
            }
        return result;
       }
}
