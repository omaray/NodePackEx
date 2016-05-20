package com.node.packex.loader;

import com.google.gson.Gson;
import com.node.packex.NodeConstants;
import com.node.packex.model.Month;
import com.node.packex.model.Year;
import com.packex.connector.HttpConnector;
import com.packex.model.pkgmgr.NodeDownloadData;
import com.packex.model.pkgmgr.RubyDownloadData;

public class NodeMonthLoader {
    Year year;
    Month month;
    String packageName;
    String url;
    String startDay;
    String endDay;
    NodeDownloadData data;
    
    public NodeMonthLoader(String packageName, Month month, Year year) {
        this.packageName = packageName;
        this.month = month;
        this.year = year;
        
        this.startDay = String.format(month.monthStartDay, year.yearValue);
        this.endDay = String.format(month.monthEndDay, year.yearValue);
        this.url = String.format(NodeConstants.NODE_MONTH_URL_TEMPLATE, startDay, endDay, this.packageName);
    }
    
    public void loadData() {
        HttpConnector connector = HttpConnector.getInstance();
        String response = connector.get(this.url);
        
        Gson gson = new Gson();
        this.data = gson.fromJson(response, RubyDownloadData.class);
    }
    
    public NodeDownloadData getDownloadData() {
        return null;
    }
}
