package team4.retailsystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Product;


public class PredictionPanel extends JPanel{

    private ArrayList<Order> orders = Database.getInstance().getOrders();
    private ArrayList<Invoice> invoices = Database.getInstance().getInvoices();
    private ArrayList<Product> products = Database.getInstance().getProducts();
    private ArrayList<LineItem> lineItem = new ArrayList<>();
    private int totalProduct = 0;
    private PiePlot3D piePlot;
    private JFreeChart chart;
    private DefaultPieDataset pieDataset;
    private CategoryDataset categoryDataset;
    private CategoryPlot categoryPlot;
    private NumberAxis rangeAxis;
    private LineAndShapeRenderer lineRenderer;
 
    private JPanel yearlyPanel;
    private JPanel monthlyPanel;
    private JPanel combinePanel;
    private JPanel infoPanel;
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private JTable infoTable;
    private DefaultTableModel model;
    private JScrollPane infoScrollPanel;
    private Object [][] list = null;
    private String [] columnNames = {"Product", "Buy", "Sell", "Profit", "Currently Stock"};
    private static int yearlyChart = 1;
    private static int monthlyChart = 2;
    private SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

    public PredictionPanel(int month, int year){
    	this.setPreferredSize(new Dimension(800,490));
        this.setLayout(new GridLayout(2, 0));       
        yearlyPanel = new JPanel();
        addPanelName("Yearly Selling", yearlyPanel);
        this.add(yearlyPanel);
        yearlyPanel.add(LineChart(year, yearlyChart));
        combinePanel = new JPanel();
        combinePanel.setLayout(new GridLayout(1, 2));
        this.add(combinePanel);
        
        monthlyPanel = new JPanel();
        addPanelName("Monthly Selling", monthlyPanel);
        monthlyPanel.add(PieChart(month, year, monthlyChart));
        combinePanel.add(monthlyPanel);
        
        infoPanel = new JPanel();
        addPanelName("Prediction Information", infoPanel);
        combinePanel.add(infoPanel);
        list = null;
        model = new DefaultTableModel(list, columnNames) {
            boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        infoTable = new JTable(model);
        infoTable.setRowHeight(30);
        infoTable.getTableHeader().setReorderingAllowed(false);
        infoScrollPanel = new JScrollPane(infoTable);
        infoScrollPanel.setPreferredSize(new Dimension(380,210));
        infoPanel.add(infoScrollPanel);
        getListInfoOfMonth(month, year);
        this.setVisible(true);
    }
    public ChartPanel PieChart(int month, int year, int type){
        pieDataset = createMonthlyPredictionDataset(month, year, type);
        chart = create3DPieChart(pieDataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380,210));
        return chartPanel;
    }
     
    public ChartPanel LineChart(int year, int type){
        categoryDataset = createYearlyDataset(year, type);
        chart = createLineChart(categoryDataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(780,210));
        return chartPanel;
    }
    
    public DefaultPieDataset createMonthlyPredictionDataset(int month, int year, int type){
        lineItem.clear();
        DefaultPieDataset dataset = new DefaultPieDataset();
        lineItem = monthOfYearPrediction(month, year, type);
        for(LineItem item : lineItem){
            Product product = Database.getInstance().getProduct(item.getProductID());
            dataset.setValue(product.getName(), new Double(item.getQuantity()*100/totalProduct));
        }
        return dataset;
    }
    
    public CategoryDataset createYearlyDataset(int year, int type){
        lineItem.clear();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 0; i < 12; i++){
            lineItem = monthOfYearPrediction(i+1, year, type);
            if(!lineItem.isEmpty()){
                for(LineItem item : lineItem){
                    Product product = Database.getInstance().getProduct(item.getProductID());
                    dataset.setValue(item.getQuantity(), product.getName(), months[i]);
                }
            }
            
        }
        return dataset;
    }
    
    public ArrayList<LineItem> monthOfYearPrediction(int month, int year, int type){
        ArrayList<LineItem> lineItem = new ArrayList<>();
        totalProduct = 0;
        for(Product product: products){
            int value = 0;
            for(Invoice invoice : invoices){
                if((Integer.valueOf(sdfMonth.format(invoice.getDate())) == month) && (Integer.valueOf(sdfYear.format(invoice.getDate())) == year)){
                    for(LineItem item : invoice.getLineItems()){
                        if(product.getID() == item.getProductID()){
                            value += item.getQuantity();
                            totalProduct += item.getQuantity();
                          }
                    }
                }
            }
            lineItem.add(new LineItem(product.getID(), value));
        }
        if(totalProduct == 0 && type == 2){
            lineItem.clear();
        }
        return lineItem;
    }
    
    
    
    public JFreeChart create3DPieChart(DefaultPieDataset dataset){
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Monthly Selling Pie Chart",  // chart title
                dataset,                   // data
                true,                   // include legend
                true,
                false
            );
        chart.setBackgroundPaint(Color.yellow);
        piePlot = (PiePlot3D) chart.getPlot();
        piePlot.setStartAngle(300);
        piePlot.setDirection(Rotation.ANTICLOCKWISE);
        piePlot.setForegroundAlpha(0.5f);
        piePlot.setNoDataMessage("No Selling to display");
        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
                "{0} = {2}", new DecimalFormat("0"), new DecimalFormat("0.00%"));
        piePlot.setLabelGenerator(generator);
        return chart;
    }

    public void addPanelName(String panelName, JPanel panel) {
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }
    
    private JFreeChart createLineChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart("Stock Level", // chart title
                "Months", // domain axis label
                "Value", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tool tips
                false // URLs?
                );
        chart.setBackgroundPaint(Color.white);
        // get a reference to the plot for further customization...
        categoryPlot = chart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(Color.WHITE);
        categoryPlot.setDomainGridlinePaint(Color.BLACK);
        categoryPlot.setRangeGridlinePaint(Color.BLACK);

        // set the range axis to display integers only...
        rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        lineRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
        for(int i = 0; i < products.size(); i++){
            lineRenderer.setSeriesLinesVisible(i, false);
            lineRenderer.setSeriesShapesVisible(i, true);
        }        
        return chart;
    }
    
    public void getListInfoOfMonth(int month, int year){
        list = null;
        model.setDataVector(list, columnNames);
        for(Product product : products){
            int sellItems = 0;
            int buyItems = 0;
            double totalProfit = 0;  
            for(Invoice invoice : invoices){
                if((Integer.valueOf(sdfMonth.format(invoice.getDate())) == month) && (Integer.valueOf(sdfYear.format(invoice.getDate())) == year)){
                    for(LineItem item : invoice.getLineItems()){
                        if(product.getID() == item.getProductID()){
                            sellItems += item.getQuantity();
                          }
                    }
                }
            }
            for(Order order : orders){
                if((Integer.valueOf(sdfMonth.format(order.getOrderDate())) == month) && (Integer.valueOf(sdfYear.format(order.getOrderDate())) == year)){
                    for(LineItem item : order.getLineItems()){
                        if(product.getID() == item.getProductID()){
                            buyItems += item.getQuantity();
                          }
                    }
                }
            }
            totalProfit = (sellItems - buyItems) * product.getMarkup();
            Object[] itemInfo = { product.getName(), buyItems, sellItems, totalProfit, product.getStockLevel()};
            model.addRow(itemInfo);
        }
        model.fireTableDataChanged();
    }
}
