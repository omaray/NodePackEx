package com.node.packex.model;

public class Month {
    public Integer monthInt;
    public String monthName;
    public String monthStartDay;
    public String monthEndDay;
    
    public Month(Integer monthInt, String monthName, String monthStartDay, String monthEndDay) {
        this.monthInt = monthInt;
        this.monthName = monthName;
        this.monthStartDay = monthStartDay;
        this.monthEndDay = monthEndDay;
    }
}
