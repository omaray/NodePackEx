package com.node.packex.loader;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.node.packex.NodeConstants;
import com.packex.connector.HttpConnector;
import com.packex.model.pkgmgr.NodeDownloadData;
import com.packex.model.pkgmgr.NodeRawData;

public class NodeDayLoader extends NodeTimeLoaderBase {
    private NodeRawData data;
    
    public NodeDayLoader(String packageName, LocalDate startDate, LocalDate endDate) {
        super(packageName, startDate);
        
        this.endDate = endDate;
        this.url = String.format(NodeConstants.NODE_RANGE_URL_TEMPLATE, this.startDate, this.endDate, this.packageName);
    }
    
    public void loadData() {
        HttpConnector connector = HttpConnector.getInstance();
        String response = connector.get(this.url);
        
        Gson gson = new Gson();
        this.data = gson.fromJson(response, NodeRawData.class);
    }
    
    public NodeRawData getDownloadData() {
        return this.data;
    }
    
    public static void main(String[] args) {
        NodeDayLoader loader = new NodeDayLoader("gcloud", LocalDate.of(2015, 5, 1), LocalDate.of(2016, 5, 1));
        loader.loadData();
        
        System.out.println(loader.getStartDate());
        System.out.println(loader.getEndDate());
        NodeRawData rawData = loader.getDownloadData();
        for (NodeDownloadData data : rawData.getDownloads()) {
            System.out.print(data.getDay());
            System.out.println(" " + data.getDownloads());
        }
    }
}
