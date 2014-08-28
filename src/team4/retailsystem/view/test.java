package team4.retailsystem.view;

import java.util.ArrayList;

import javax.swing.JFrame;

import team4.retailsystem.model.Database;

public class test extends JFrame{

    public test() {
        // TODO Auto-generated constructor stub
        this.setSize(820, 400);
        this.setLocation(420,120);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Log In Frame");

        this.setContentPane(new StockControlPanel());

        this.setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        new test();
    }
}