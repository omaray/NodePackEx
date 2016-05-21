package com.node.packex.manager;

import java.util.HashMap;
import java.util.Map;

import com.node.packex.NodeConstants;
import com.node.packex.NodeUtil;
import com.node.packex.connector.NodeBigQueryConnector;
import com.node.packex.loader.NodeDayLoader;
import com.packex.Constants;
import com.packex.model.pkgmgr.NodeDownloadData;
import com.packex.model.pkgmgr.NodeRawData;

public class NodeDayManager extends NodeTimeManagerBase {
    
    public NodeDayManager(
            NodeBigQueryConnector connector, String packageName, String companyName, String category) {
        super(connector, packageName, companyName, category);
    }
    
    public void saveData() {
        NodeDayLoader loader = new NodeDayLoader(packageName, this.startDate, this.endDate);
        loader.loadData();
        
        NodeRawData rawData = loader.getDownloadData();
        for (NodeDownloadData data : rawData.getDownloads()) {
            Map<String, Object> row = new HashMap<String, Object>();
            row.put(Constants.DATE_FIELD, data.getDay() + Constants.NEW_DAY_TIME);
            row.put(NodeConstants.COMPANY_FIELD, this.companyName);
            row.put(Constants.PACKAGE_NAME_FIELD, this.packageName);
            row.put(Constants.VERSION_FIELD, Constants.ALL_VERSIONS);
            row.put(Constants.CATEGORY_FIELD, this.category);
            row.put(Constants.TOTAL_DOWNLOADS_FIELD, data.getDownloads());
            
            this.connector.addRow(row);
        }
    }
    
    public static void main(String[] args) {
        String datasetName = NodeUtil.getNodeDatasetName();
        String tableName = NodeUtil.getNodeTableName(NodeConstants.DAY);
        
        NodeBigQueryConnector connector = NodeBigQueryConnector.getInstance();
        connector.createDataset(datasetName);
        connector.createDayTable(datasetName, tableName);
        connector.begin(datasetName, tableName);
        
        NodeDayManager manager = new NodeDayManager(connector, "gcloud", "google", "cloud");
        manager.saveData();
        
        connector.commit();
    }
}
