package com.tingge.pojo;

public class WriteBackDate {
    private String sheetName;
    private String CaseId;
    private String CellName;
    private String result;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public String getCellName() {
        return CellName;
    }

    public void setCellName(String cellName) {
        CellName = cellName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public WriteBackDate() {
    }

    public WriteBackDate(String sheetName, String CaseId, String cellName, String result) {
        super();
        this.sheetName = sheetName;
        this.CaseId = CaseId;
        this.CellName = cellName;
        this.result = result;
    }
}
