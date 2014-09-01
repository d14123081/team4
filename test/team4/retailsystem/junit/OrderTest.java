package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.awt.ItemSelectable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import team4.retailsystem.model.*;
public class OrderTest {

    ArrayList<LineItem> items1 = new ArrayList<>();
    ArrayList<LineItem> items2 = new ArrayList<>();
    Supplier supplier = new Supplier("Apple", "Apple Address", "apple@yahoo.com", "1234567");
    LineItem item1 = new LineItem(1, 10);
    LineItem item2 = new LineItem(2, 20);
    Order order = new Order(7500, supplier, 1, items1);
    @Test
    public void testGetLineItems() {
        items1.add(item1);
        items1.add(item2);      
        assertEquals(item1, order.getLineItems().get(0));
    }

    @Test
    public void testSetLineItems() {
        items2.add(item2);
        order.setLineItems(items2);
        assertEquals(items2,order.getLineItems());
    }

    @Test
    public void testGetID() {
        assertNotNull(order.getID());
    }

    @Test
    public void testGetCost() {
        assertNotSame("cost cant be set, it's calculated base on each item cost"  , 5000,order.getCost());
    }

    @Test
    public void testSetCost() {        
        order.setCost(5000);
        assertNotSame("cost cant be set, it's calculated base on each item cost"  , 5000,order.getCost());
    }

    @Test
    public void testGetSupplier() {
        assertEquals(supplier, order.getSupplier());
    }

    @Test
    public void testSetOrderDate() {
        try {
            order.setOrderDate(new SimpleDateFormat("dd/MM/yyyy").parse("11/12/2014"));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals("11/12/2014", new SimpleDateFormat("dd/MM/yyyy").format(order.getOrderDate()));
        
    }

}
