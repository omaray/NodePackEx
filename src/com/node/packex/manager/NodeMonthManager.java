package com.node.packex.manager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.node.packex.NodeConstants;
import com.node.packex.NodeUtil;
import com.node.packex.connector.NodeBigQueryConnector;
import com.node.packex.loader.NodeMonthLoader;
import com.packex.Constants;

public class NodeMonthManager {
    private static final Integer TIMELINE = 16;
    String packageName;
    String companyName;
    String category;
    LocalDate endDate;
    NodeBigQueryConnector connector;
    
    public NodeMonthManager(
            NodeBigQueryConnector connector, String packageName, String companyName, String category) {
        this.connector = connector;
        this.packageName = packageName;
        this.companyName = companyName;
        this.category = category;
        
        LocalDate currentDate = LocalDate.now();
        this.endDate = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);
    }

    public void saveData() {        
        LocalDate manyMonthsAgo = this.endDate.minusMonths(TIMELINE);
        
        for (LocalDate date = manyMonthsAgo; date.isBefore(this.endDate); date = date.plusMonths(1)) {            
            NodeMonthLoader loader = new NodeMonthLoader(this.packageName, date);
            loader.loadData();
            
            Map<String, Object> row = new HashMap<String, Object>();
            row.put(Constants.DATE_FIELD, date + Constants.NEW_DAY_TIME);
            row.put(NodeConstants.YEAR_FIELD, date.getYear());
            row.put(NodeConstants.MONTH_FIELD, date.getMonth().name().toUpperCase());
            row.put(NodeConstants.COMPANY_FIELD, this.companyName);
            row.put(Constants.PACKAGE_NAME_FIELD, this.packageName);
            row.put(Constants.VERSION_FIELD, Constants.ALL_VERSIONS);
            row.put(Constants.CATEGORY_FIELD, this.category);
            row.put(Constants.TOTAL_DOWNLOADS_FIELD, loader.getDownloadData().getDownloads());
            
            this.connector.addRow(row);
        }
    }
    
    public static void main(String[] args) {
        String datasetName = NodeUtil.getNodeDatasetName();
        String tableName = NodeUtil.getNodeTableName(NodeConstants.MONTH);
        
        NodeBigQueryConnector connector = NodeBigQueryConnector.getInstance();
        connector.createDataset(datasetName);
        connector.createMonthTable(datasetName, tableName);
        connector.begin(datasetName, tableName);
        
        NodeMonthManager manager = new NodeMonthManager(connector, "gcloud", "google", "cloud");
        manager.saveData();
        
        connector.commit();
    }
}
