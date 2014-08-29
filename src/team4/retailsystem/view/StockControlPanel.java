package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

public class StockControlPanel extends JPanel implements ActionListener {
    
    private JPanel buttonPanel;
    private JPanel viewPanel;
    private JButton stockLevelButton;
    private JButton predictionButton;
    private JScrollPane chartScrollPanel;
    private DisplayChart displayChart = new DisplayChart();

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
        

        chartScrollPanel = new JScrollPane(displayChart.Chart(2));
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
}
