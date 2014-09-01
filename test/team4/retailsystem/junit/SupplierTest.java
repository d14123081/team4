package team4.retailsystem.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import team4.retailsystem.model.Supplier;

public class SupplierTest {

    Supplier supplier = new Supplier("supplier1", "address1", "email1", "telephone1");
    @Test
    public void testGetName() {
        assertEquals("supplier1", supplier.getName());
    }

    @Test
    public void testSetName() {
        supplier.setName("supplier2");
        assertEquals("supplier2", supplier.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("email1", supplier.getEmail());
    }

    @Test
    public void testSetEmail() {
        supplier.setEmail("email2");
        assertEquals("email2", supplier.getEmail());
    }

    @Test
    public void testGetAddress() {
        assertEquals("address1", supplier.getAddress());
    }

    @Test
    public void testSetAddress() {
        supplier.setAddress("address2");
        assertNotSame("address1", supplier.getAddress());
        assertEquals("address2", supplier.getAddress());
    }

    @Test
    public void testGetTelephoneNumber() {
        assertEquals("telephone1", supplier.getTelephoneNumber());
    }

    @Test
    public void testSetTelephone() {
        supplier.setTelephone("123456");
        assertEquals("123456", supplier.getTelephoneNumber());
    }
}
