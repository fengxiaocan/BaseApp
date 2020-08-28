package com.app.base.litepal;

public enum Condition{
    in(" in "),
    like(" like "),
    equalTo(" = ?"),
    moreThan(" > ?"),
    lessThan(" < ?"),
    notMoreThan(" <= ?"),
    notLessThan(" >= ?");
    private String value;

    Condition(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
