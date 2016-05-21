package com.node.packex.loader;

import java.time.LocalDate;

public abstract class NodeTimeLoaderBase {
    protected String packageName;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String url;

    public NodeTimeLoaderBase(String packageName, LocalDate startDate) {
        this.packageName = packageName;
        this.startDate = startDate;
    }
    
    abstract public void loadData();
    
    public LocalDate getStartDate() {
        return this.startDate;
    }
    
    public LocalDate getEndDate() {
        return this.endDate;
    }
}
