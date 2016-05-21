package com.node.packex.loader;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.node.packex.NodeConstants;
import com.packex.connector.HttpConnector;
import com.packex.model.pkgmgr.NodeDownloadData;

public class NodeMonthLoader {
    String packageName;
    LocalDate startDate;
    LocalDate endDate;
    String url;
    NodeDownloadData data;
    
    public NodeMonthLoader(String packageName, LocalDate startDate) {
        this.packageName = packageName;
        this.startDate = startDate;
        
        int monthLength = this.startDate.lengthOfMonth();             
        this.endDate = this.startDate.plusDays(monthLength - 1);
        this.url = String.format(NodeConstants.NODE_MONTH_URL_TEMPLATE, this.startDate, this.endDate, this.packageName);
    }
    
    public void loadData() {
        HttpConnector connector = HttpConnector.getInstance();
        String response = connector.get(this.url);
        
        Gson gson = new Gson();
        this.data = gson.fromJson(response, NodeDownloadData.class);
    }
    
    public LocalDate getStartDate() {
        return this.startDate;
    }
    
    public LocalDate getEndDate() {
        return this.endDate;
    }
    
    public NodeDownloadData getDownloadData() {
        return this.data;
    }
    
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalDate currentDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        
        LocalDate oneMonthAgo = currentDate.minusMonths(1);
        NodeMonthLoader loaderJan = new NodeMonthLoader("gcloud", oneMonthAgo);
        loaderJan.loadData();
        NodeDownloadData dataJan = loaderJan.getDownloadData();
        System.out.println(loaderJan.getStartDate());
        System.out.println(loaderJan.getEndDate());
        System.out.println(dataJan.getDownloads());
        
        LocalDate twoMonthsAgo = currentDate.minusMonths(2);
        NodeMonthLoader loaderFeb = new NodeMonthLoader("gcloud", twoMonthsAgo);
        loaderFeb.loadData();
        NodeDownloadData dataFeb = loaderFeb.getDownloadData();
        System.out.println(loaderFeb.getStartDate());
        System.out.println(loaderFeb.getEndDate());
        System.out.println(dataFeb.getDownloads());
    }
}
