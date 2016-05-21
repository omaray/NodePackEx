package com.node.packex.manager;

import java.time.LocalDate;

import com.node.packex.connector.NodeBigQueryConnector;

public abstract class NodeTimeManagerBase {
    protected static final Integer TIMELINE = 16;
    protected String packageName;
    protected String companyName;
    protected String category;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected NodeBigQueryConnector connector;
    
    public NodeTimeManagerBase(
            NodeBigQueryConnector connector, String packageName, String companyName, String category) {
        this.connector = connector;
        this.packageName = packageName;
        this.companyName = companyName;
        this.category = category;
        
        LocalDate currentDate = LocalDate.now();
        this.endDate = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);
        this.startDate = this.endDate.minusMonths(TIMELINE);
    }
    
    abstract public void saveData(); 
}
