package team4.retailsystem.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Product;

import java.awt.Color;
import java.awt.GradientPaint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DisplayChart {

    private CategoryDataset dataset;
    private JFreeChart chart;
    private CategoryPlot plot;
    private NumberAxis rangeAxis;
    private BarRenderer barRenderer;
    private LineAndShapeRenderer lineRenderer;
    private GradientPaint gp0;
    private GradientPaint gp1;
    private GradientPaint gp2;
    private ChartPanel chartPanel;

    private ArrayList<Product> products = Database.getInstance().getProducts();
    private ArrayList<Invoice> invoices = Database.getInstance().getInvoices();
    private ArrayList<Order> orders = Database.getInstance().getOrders();
    private ArrayList<Date> dates = new ArrayList<>();
    // Data series
    private String buySeries = "Buy";
    private String sellSeries = "Sell";
    private String stockSeries = "Stock Level";
    private String profitSeries = "Profit";

    private int orderItemValue;
    private int invoiceItemValue;
    private double orderCost;
    private double invoiceCost;
    private double profits;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ChartPanel Chart(int type) {
        dataset = createDataset(type);
        chart = createChart(dataset, type);
        chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    private CategoryDataset createDataset(int type) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (type == 1) {
            getStockLevelDataSet(dataset);
        }

        else if (type == 2) {
            getProfitDataSet(dataset);
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset, int type) {
        JFreeChart chart = getChartType(type);
        chart.setBackgroundPaint(Color.white);
        // get a reference to the plot for further customization...
        plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        // set the range axis to display integers only...
        rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        if (type == 1) {
            // disable bar outlines...
            barRenderer = (BarRenderer) plot.getRenderer();
            barRenderer.setDrawBarOutline(false);

            // set up gradient paints for series...
            gp0 =
                    new GradientPaint(0.0f, 0.0f, Color.BLUE, 0.0f, 0.0f,
                            Color.BLUE);
            gp1 =
                    new GradientPaint(0.0f, 0.0f, Color.GREEN, 0.0f, 0.0f,
                            Color.GREEN);
            gp2 =
                    new GradientPaint(0.0f, 0.0f, Color.RED, 0.0f, 0.0f,
                            Color.RED);
            barRenderer.setSeriesPaint(0, gp0);
            barRenderer.setSeriesPaint(1, gp1);
            barRenderer.setSeriesPaint(2, gp2);
            barRenderer.setBaseItemLabelsVisible(true);
            DecimalFormat decimalformat1 = new DecimalFormat("##,###");
            barRenderer
                    .setItemLabelGenerator(new StandardCategoryItemLabelGenerator(
                            "{2}", decimalformat1));
            barRenderer.setPositiveItemLabelPosition(new ItemLabelPosition(
                    ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        }

        else if (type == 2) {
            lineRenderer = (LineAndShapeRenderer) plot.getRenderer();
            lineRenderer.setSeriesLinesVisible(0, false);
            lineRenderer.setSeriesShapesVisible(0, true);
            lineRenderer.setSeriesLinesVisible(1, false);
            lineRenderer.setSeriesShapesVisible(1, true);
            lineRenderer.setSeriesLinesVisible(2, true);
        }

        return chart;
    }

    public int getOrderItemValue(Product product) {
        int value = 0;
        Date d = new Date();
        int month = d.getMonth() - 2;
        Date d1 = new Date();
        d1.setMonth(month);
        
        for (Order order : orders) {
            if(order.getOrderDate().after(d1)){
                for (LineItem item : order.getLineItems()) {
                    if (product.getID() == item.getProductID()) {
                        value += item.getQuantity();
                    }
                }
            }   
        }
        return value;
    }

    public int getInvoiceItemValue(Product product) {
        int value = 0;
        Date d = new Date();
        int month = d.getMonth() - 2;
        Date d1 = new Date();
        d1.setMonth(month);      
        for (Invoice invoice : invoices) {
            if(invoice.getDate().after(d1)){
                for (LineItem item : invoice.getLineItems()) {
                    if (product.getID() == item.getProductID()) {
                        value += item.getQuantity();
                    }
                }
            }         
        }
        return value;
    }

    public double getOrderCost(Date date) {
        double cost = 0;
        for (Order order : orders) {
            if (order.getOrderDate().equals(date)) {
                cost = order.getCost();
            }
        }
        return cost;
    }

    public double getInvoiceCost(Date date) {
        double cost = 0;
        for (Invoice invoice : invoices) {
            if (invoice.getDate().equals(date)) {
                cost = invoice.getCost();
            }
        }
        return cost;
    }
    
    private JFreeChart getChartType(int type) {
        JFreeChart chart = null;
        if (type == 1) {
            chart = ChartFactory.createBarChart("Stock Level", // chart title
                    "Products", // domain axis label
                    "Value", // range axis label
                    dataset, // data
                    PlotOrientation.VERTICAL, // orientation
                    true, // include legend
                    true, // tool tips
                    false // URLs?
                    );
        }

        else if (type == 2) {
            chart = ChartFactory.createLineChart("Profit Level", // chart title
                    "Date", // domain axis label
                    "Value", // range axis label
                    dataset, // data
                    PlotOrientation.VERTICAL, // orientation
                    true, // include legend
                    true, // tool tips
                    false // URLs?
                    );
        }
        return chart;
    }

    private ArrayList<Date> getDate() {
        ArrayList<Date> dates = new ArrayList<>();
        Date d = new Date();
        int month = d.getMonth() - 2;
        Date d1 = new Date();
        d1.setMonth(month);
        for (Order order : orders) {
            if(order.getOrderDate().after(d1)){
                dates.add(order.getOrderDate());
            }
            
        }
        for (Invoice invoice : invoices) {
            for (Date date : dates) {
                if(invoice.getDate().after(d1)){
                    if (invoice.getDate().equals(date)) {
                        break;
                    } else if (invoice.getDate().after(date)
                            && !invoice.getDate().equals(date)) {
                        dates.add(dates.indexOf(date), invoice.getDate());
                        break;
                    }
                }
                
            }
        }
        Collections.sort(dates);
        return dates;
    }
   
    public void getStockLevelDataSet(DefaultCategoryDataset dataset){
        for (Product product : products) {
            orderItemValue = getOrderItemValue(product);
            dataset.addValue(orderItemValue, buySeries, product.getName());

            invoiceItemValue = getInvoiceItemValue(product);
            dataset.addValue(invoiceItemValue, sellSeries,
                    product.getName());

            dataset.addValue(product.getStockLevel(), stockSeries,
                    product.getName());
        }
    }
    
    public void getProfitDataSet(DefaultCategoryDataset dataset){
        dates.clear();
        dates = getDate();
        profits = 0;
        int i = 0;
        for (Date d : dates) {
            orderCost = getOrderCost(d);
            invoiceCost = getInvoiceCost(d);
            if(i == 0){
                dataset.addValue(orderCost, buySeries, sdf.format(d.getTime()));
                dataset.addValue(invoiceCost, sellSeries,
                        sdf.format(d.getTime()));
            }
            else {
                if(orderCost > 0)
                    dataset.addValue(orderCost, buySeries, sdf.format(d.getTime()));
                                  
                
                else if(invoiceCost > 0)
                    dataset.addValue(invoiceCost, sellSeries,
                            sdf.format(d.getTime()));
            }
            
                        
            profits += (invoiceCost - orderCost);
            dataset.addValue(profits, profitSeries, sdf.format(d.getTime()));
            invoiceCost = 0;
            orderCost = 0;
            i++;
        }
    }
    
}
