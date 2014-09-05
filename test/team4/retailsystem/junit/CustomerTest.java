package team4.retailsystem.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import team4.retailsystem.model.Customer;

public class CustomerTest {

	@Test
	public void test() {
		Customer c = new Customer("Vodaphone", "Address", "Email", "012345", 1);

		assertEquals(c.getTelephoneNumber(), "Address");
		assertEquals(c.getName(), "Vodaphone");
		assertEquals(c.getAddress(), "012345");
		assertEquals(c.getEmail(), "Email");
		assertEquals(c.getID(), 1);

		c.setAddress("Address");
		c.setEmail("Email");
		c.setName("O2");
		c.setTelephoneNumber("012345");

		assertEquals(c.getTelephoneNumber(), "012345");
		assertEquals(c.getName(), "O2");
		assertEquals(c.getAddress(), "Address");
		assertEquals(c.getEmail(), "Email");
		assertEquals(c.getID(), 1);
	}
}
