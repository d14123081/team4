package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private DisplayChart displayChart;
    private static int stockView = 1;
    private static int profitView = 2;
    private boolean isStock = false;
    private boolean isProfit = false;

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
               
        //stockChartScrollPanel = new JScrollPane(new XYchart().xyChart()); 
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
            viewPanel.removeAll();
            viewPanel.revalidate();
            displayChart = new DisplayChart();
            stockChartScrollPanel = new JScrollPane(displayChart.Chart(stockView));
            viewPanel.add(stockChartScrollPanel);
            isStock = true;
            isProfit = false;
        }
        
        else if(arg0.getSource().equals(profitLevelButton)){
            viewPanel.removeAll();
            viewPanel.revalidate();
            displayChart = new DisplayChart();
            profitChartScrollPanel = new JScrollPane(displayChart.Chart(profitView));
            viewPanel.add(profitChartScrollPanel);
            viewPanel.validate();
            isStock = false;
            isProfit = true;
        }
        
        else if(arg0.getSource().equals(predictionButton)){
            
        }
        
    }

    public void updateOrderList(ArrayList<Order> orders) {
        updateChart();
    }

    public void updateChart(){
        viewPanel.removeAll();
        viewPanel.revalidate();
        if(isStock == true){
            displayChart = new DisplayChart();
            stockChartScrollPanel = new JScrollPane(displayChart.Chart(stockView));
            viewPanel.add(stockChartScrollPanel);
        }
        else if(isProfit == true){
            displayChart = new DisplayChart();
            profitChartScrollPanel = new JScrollPane(displayChart.Chart(profitView));
            viewPanel.add(profitChartScrollPanel);
        }      
        viewPanel.validate();
    }
}
