package team4.retailsystem.view;

import javax.swing.JFrame;

public class test extends JFrame{

    public test() {
        // TODO Auto-generated constructor stub
        this.setSize(500, 500);
        this.setLocation(420,120);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Log In Frame");

        this.setContentPane(new SupplierPanel());

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
