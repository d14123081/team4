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
    private DisplayChart displayChart;
    private PredictionPanel predictionChart;
    private static int stockView = 1;
    private static int profitView = 2;
    private boolean isStock = false;
    private boolean isProfit = false;
    private boolean isPrediction = false;
    private Date date = new Date();
    private int month = date.getMonth();

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
            predictionChart = new PredictionPanel(8,date.getYear());
            predictionChartScrollPanel = new JScrollPane(predictionChart);
            //predictionChartScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            viewPanel.add(predictionChartScrollPanel);
            viewPanel.validate();
            isStock = false;
            isPrediction = true;
            isProfit = false;
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
            //predictionPanel.setLayout(mgr);
            predictionChart = new PredictionPanel(new Date().getMonth(), date.getYear());
            predictionChartScrollPanel = new JScrollPane(predictionChart);
            predictionChartScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            viewPanel.add(predictionChartScrollPanel);}
        viewPanel.validate();
    }
}
