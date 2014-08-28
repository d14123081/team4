package team4.retailsystem.view;

import org.jfree.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Product;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class StockControlPanel extends JPanel implements ActionListener {
    
    private JPanel buttonPanel;
    private JPanel viewPanel;
    private ChartPanel chartPanel;
    private JButton stockLevelButton;
    private JButton predictionButton;
    private JScrollPane chartScrollPanel;
    private CategoryDataset dataset;
    private JFreeChart chart;
    private CategoryPlot plot;
    private NumberAxis rangeAxis;
    private BarRenderer renderer;
    private GradientPaint gp0;
    private GradientPaint gp1;
    private GradientPaint gp2;
    private CategoryAxis domainAxis;
    
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Invoice> invoices = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    
    //Data series
    private String series1 = "Buy";
    private String series2 = "Sell";
    private String series3 = "Stock Level";
    
    private int orderItemValue;
    private int invoiceItemValue;

    public StockControlPanel() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 30;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        addPanel(buttonPanel, gbl, gbc);
        
        stockLevelButton = new JButton("Stock Level");
        stockLevelButton.addActionListener(this);
        buttonPanel.add(stockLevelButton);
        
        predictionButton = new JButton("Prediction");
        predictionButton.addActionListener(this);
        buttonPanel.add(predictionButton);
        
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        viewPanel = new JPanel();
        addPanel(viewPanel, gbl, gbc);
        
        dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        chartScrollPanel = new JScrollPane(chartPanel);
        addPanelName("Chart", chartScrollPanel);
        chartScrollPanel.setVisible(false);
        viewPanel.add(chartScrollPanel);
 
        
        
        this.setVisible(true);
    }

    public void addPanel(JPanel panel, GridBagLayout gbl, GridBagConstraints gbc) {
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }
    
    public void addScrollPane(JScrollPane panel, GridBagLayout gbl,
            GridBagConstraints gbc) {
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }
    
    public void addPanelName(String panelName, JScrollPane panel) {
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource().equals(stockLevelButton)){
            chartScrollPanel.setVisible(true);
            viewPanel.validate();
        }
        
    }
    
    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        products = Database.getInstance().getProducts();
        orders = Database.getInstance().getOrders();
        invoices = Database.getInstance().getInvoices();
        
        String[] category = new String[products.size()];
        int i = 0;
        for(Product product : products){
            category[i] = product.getName();
                      
            orderItemValue = getOrderItemValue(product, orders);                      
            dataset.addValue(orderItemValue, series1, category[i]);
            
            invoiceItemValue = getInvoiceItemValue(product, invoices);           
            dataset.addValue(invoiceItemValue, series2, category[i]);
            
            dataset.addValue(product.getStockLevel(), series3, category[i]);
            
            i++;
        }
        
        return dataset;
    }
    
    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Stock Level",         // chart title
                "Products",               // domain axis label
                "Value",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
            );
        chart.setBackgroundPaint(Color.white);
        // get a reference to the plot for further customization...
        plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        // set the range axis to display integers only...
        rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);
        
        domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        
        return chart;
    }
    
    public int getOrderItemValue(Product product, ArrayList<Order> orders){
        int value = 0;
        for(Order order : orders){
            for(LineItem item : order.getLineItems()){
                if(product.getID()== item.getProductID()){
                    value = value + item.getQuantity();
                }
            }
        }
        return value;
    }
    
    public int getInvoiceItemValue(Product product, ArrayList<Invoice> invoices){
        int value = 0;
        for(Invoice invoice : invoices){
            for(LineItem item : invoice.getLineItems()){
                if(product.getID()== item.getProductID()){
                    value = value + item.getQuantity();
                }
            }
        }
        return value;
    }
}
