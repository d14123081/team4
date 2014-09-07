package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import team4.retailsystem.model.Order;

public class StockControlPanel extends JPanel implements ActionListener {
    
    private JPanel buttonPanel;
    private JPanel viewPanel;
    private JButton stockLevelButton;
    private JButton profitLevelButton;
    private JButton predictionButton;
    private JScrollPane stockChartScrollPanel;
    private JScrollPane profitChartScrollPanel;
    private JScrollPane predictionChartScrollPanel;
    private JPanel predictionPanel;
    private JPanel comboBoxPanel;
    private DisplayChart displayChart;
    private PredictionPanel predictionChart;
    private static int stockView = 1;
    private static int profitView = 2;
    private boolean isStock = false;
    private boolean isProfit = false;
    private boolean isPrediction = false;
    private Date date = new Date();
    private int month = date.getMonth();
    private int year = date.getYear();
    private JLabel yearsLabel = new JLabel("Year");
    private JLabel monthLabel = new JLabel("Month");
    private JComboBox yearsComboBox;
    private JComboBox monthsComboBox;
    private String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private String[] years = new String[5];

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
        
        stockLevelButton = new JButton("Stock Chart");
        stockLevelButton.addActionListener(this);
        buttonPanel.add(stockLevelButton);
        
        profitLevelButton = new JButton("Profit Chart");
        profitLevelButton.addActionListener(this);
        buttonPanel.add(profitLevelButton);
        
        predictionButton = new JButton("Prediction");
        predictionButton.addActionListener(this);
        buttonPanel.add(predictionButton);
        
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        viewPanel = new JPanel();
        addPanel(viewPanel, gbl, gbc);
               
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
    	if(predictionChart != null){
    		predictionChartScrollPanel.setVisible(false);
    	}
        viewPanel.removeAll();
        if(arg0.getSource().equals(stockLevelButton)){
            displayChart = new DisplayChart();
            stockChartScrollPanel = new JScrollPane(displayChart.Chart(stockView));
            stockChartScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            stockChartScrollPanel.setAutoscrolls(true);
            viewPanel.add(stockChartScrollPanel);
            isStock = true;
            isProfit = false;
            isPrediction = false;
        }
        
        else if(arg0.getSource().equals(profitLevelButton)){
            displayChart = new DisplayChart();
            profitChartScrollPanel = new JScrollPane(displayChart.Chart(profitView));
            profitChartScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            viewPanel.add(profitChartScrollPanel);
            viewPanel.validate();
            isStock = false;
            isPrediction = false;
            isProfit = true;
        }
        
        else if(arg0.getSource().equals(predictionButton)){
            getPredictionPanel(month, year);
            viewPanel.validate();
            isStock = false;
            isPrediction = true;
            isProfit = false;
        }
        else if( arg0.getSource().equals(yearsComboBox) || arg0.getSource().equals(monthsComboBox)){
            month = monthsComboBox.getSelectedIndex() + 1;
            year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            getPredictionPanel(month, year);          
        }
        viewPanel.validate();
        
    }

    public void updateOrderList(ArrayList<Order> orders) {
        updateChart();
    }

    public void updateChart(){
        viewPanel.removeAll();
        if(isStock == true){
        	predictionChartScrollPanel.setVisible(false);
            displayChart = new DisplayChart();
            stockChartScrollPanel = new JScrollPane(displayChart.Chart(stockView));
            viewPanel.add(stockChartScrollPanel);
        }
        else if(isProfit == true){
            displayChart = new DisplayChart();
            profitChartScrollPanel = new JScrollPane(displayChart.Chart(profitView));
            viewPanel.add(profitChartScrollPanel);
        }  
        else if(isPrediction == true){         
            getPredictionPanel(month, year);
            viewPanel.add(predictionChartScrollPanel);}
        viewPanel.validate();
    }
    public void getPredictionPanel(int month, int year){
        predictionPanel = new JPanel();
        predictionPanel.setLayout(new BoxLayout(predictionPanel, BoxLayout.Y_AXIS));
        comboBoxPanel = new JPanel();
        predictionPanel.add(comboBoxPanel);
        comboBoxPanel.add(monthLabel);
        monthsComboBox = new JComboBox(months);
        comboBoxPanel.add(monthsComboBox);
        //monthsComboBox.setModel(new DefaultComboBoxModel(months));
        monthsComboBox.setSelectedIndex(month-1);
        monthsComboBox.addActionListener(this);
        comboBoxPanel.add(yearsLabel);
       
        for(int i = 0; i < 5; i++){
            years[i] = String.valueOf(new Date().getYear() - i);
            System.out.println(year);
        }
        yearsComboBox = new JComboBox(years);
        comboBoxPanel.add(yearsComboBox);
        yearsComboBox.setSelectedIndex(date.getYear()-year);
        yearsComboBox.addActionListener(this);
        
        predictionChart = new PredictionPanel(month, year);
        predictionChartScrollPanel = new JScrollPane(predictionChart);
        predictionChartScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        predictionPanel.add(predictionChart);
        
        viewPanel.add(predictionPanel);
    }
}
