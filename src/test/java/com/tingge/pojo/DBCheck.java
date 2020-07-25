package com.tingge.pojo;

public class DBCheck {
    private String no;
    private String sql;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "DBCheck{" +
                "no='" + no + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
