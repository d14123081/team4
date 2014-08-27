package team4.retailsystem.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CalenderPanel extends JPanel implements ActionListener {

    private JLabel label;
    private JLabel monthYearLabel;
    private JPanel panel1;
    private JPanel panel2;
    private static JTextField displayDate;
    private String[] day = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
    private JButton[] dayButton = new JButton[42];
    private JButton previousButton;
    private JButton nextButton;
    private SimpleDateFormat sdf;
    private Calendar calendar = Calendar.getInstance();
    private int month = calendar.get(Calendar.MONTH);
    private int year = calendar.get(Calendar.YEAR);
    private int dayOfWeek;
    private int daysInMonth;
    private Calendar date = Calendar.getInstance();

    public CalenderPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel panelT = new JPanel();
        displayDate = new JTextField(30);
        displayDate.setHorizontalAlignment(SwingConstants.CENTER);
        panelT.add(displayDate);
        this.add(panelT);
        panel1 = new JPanel();
        this.add(panel1);
        panel1.setLayout(new GridLayout(7, 7));
        for (int i = 0; i < 7; i++) {
            label = new JLabel(day[i]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(label);
        }
        for (int i = 0; i < 42; i++) {
            dayButton[i] = new JButton();
            dayButton[i].addActionListener(this);
            panel1.add(dayButton[i]);
        }

        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(this.getWidth(), 20));
        this.add(panel2);
        panel2.setLayout(new BorderLayout());
        JPanel panelP = new JPanel();
        previousButton = new JButton("<");
        previousButton.addActionListener(this);
        panelP.add(previousButton);
        panel2.add(panelP, BorderLayout.LINE_START);
        JPanel panelL = new JPanel();
        monthYearLabel = new JLabel();
        //monthYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelL.add(monthYearLabel);
        panel2.add(panelL, BorderLayout.CENTER);
        JPanel panelN = new JPanel();
        nextButton = new JButton(">");
        nextButton.addActionListener(this);
        panelN.add(nextButton);
        panel2.add(panelN, BorderLayout.LINE_END);
        displayDate();
    }

    public void resetButton() {
        for (int i = 0; i < 42; i++) {
            dayButton[i].setText("");
            dayButton[i].setEnabled(true);
        }
    }

    public void displayDate() {
        resetButton();
        sdf = new SimpleDateFormat("MMMM yyyy");
        calendar.set(year, month, 1);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int i = -2; // original start from sat but this panel start from
                    // Monday, therefore set i = -2
        for (int day = 1; day <= daysInMonth; day++) {
            dayButton[i + dayOfWeek].setText("" + day);
            i++;
        }
        for (int j = 0; j < 42; j++) {
            if (dayButton[j].getText().equals("")) {
                dayButton[j].setEnabled(false);
            }
        }
        monthYearLabel.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource().equals(previousButton)) {
            month--;
            displayDate();
        }

        else if (arg0.getSource().equals(nextButton)) {
            month++;
            displayDate();
        }

        else {
            date.set(year, month, Integer.parseInt(arg0.getActionCommand()));
            displayDate.setText("Delivery on: "+new SimpleDateFormat("dd/MM/yyyy").format(date.getTime()));
        }
    }

    public String getDate() {
        return displayDate.getText();
    }

}
