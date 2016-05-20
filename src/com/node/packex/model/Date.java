package com.node.packex.model;

public class Date {
    Month month;
    Year year;
    
    public Date(Month month, Year year) {
        this.month = month;
        this.year = year;
    }
    
    public Month getMonth() {
        return this.month;
    }
    
    public Year getYear() {
        return this.year;
    }
}
