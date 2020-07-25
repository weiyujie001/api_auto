package com.tingge.pojo;

public class RegisterParams {
    /**
    *@Description   RegisterParams registerParams = JSONObject.parseObject(paramters,RegisterParams.class);
    *@Param
    *@Return
    */
    String mobilephone;
    String pwd;

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "mobilephone="+mobilephone+",pwd="+ pwd;
    }
}
