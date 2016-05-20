package com.node.packex.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.node.packex.NodeConstants;
import com.node.packex.NodeUtil;
import com.node.packex.connector.NodeBigQueryConnector;
import com.node.packex.loader.NodeMonthLoader;
import com.node.packex.model.Date;
import com.node.packex.model.Month;
import com.packex.Constants;

public class NodeMonthManager {
    String packageName;
    String companyName;
    String category;
    Date startDate;
    NodeBigQueryConnector connector;
    
    public NodeMonthManager(NodeBigQueryConnector connector, Date startDate, String packageName, String companyName, String category) {
        this.connector = connector;
        this.packageName = packageName;
        this.companyName = companyName;
        this.category = category;
        this.startDate = startDate;
    }

    public void saveData() {
        List<Month> monthList = NodeUtil.getMonthList();
        for (Month month : monthList) {
            NodeMonthLoader loader = new NodeMonthLoader(this.packageName, startDate.getMonth(), startDate.getYear());
            loader.loadData();
            
            Map<String, Object> row = new HashMap<String, Object>();
            row.put(NodeConstants.MONTH_FIELD, month.monthName);
            row.put(NodeConstants.COMPANY_FIELD, this.companyName);
            row.put(Constants.PACKAGE_NAME_FIELD, this.packageName);
            row.put(Constants.VERSION_FIELD, Constants.ALL_VERSIONS);
            row.put(Constants.CATEGORY_FIELD, this.category);
            row.put(Constants.TOTAL_DOWNLOADS_FIELD, loader.getDownloadData().getDownloads());
            
            this.connector.addRow(row);
        }
    }
}
