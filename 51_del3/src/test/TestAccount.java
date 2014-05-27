package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spil.Account;

/*
 * This is a JUnit test class, they are created using:
 * rightclick on package > New > Other > Java > JUnit > "JUnit test case".
 * 
 * JUnit 4 comes pre-installed in eclipse.
 * 
 * here's a link to a guide about JUnit:
 * http://www.vogella.com/articles/JUnit/article.html
 */

public class TestAccount
{

	Account acc;
	
	//the JUnit performs this setup before each test
	@Before
	public void setUp()
	{
		 acc = new Account();
	}
	//the JUnit performs this cleanup after each test
	@After
	public void cleanUp()
	{
//		System.out.println("nothing to clean up yet");
	}
	
	//simple test to check that we start with the expected balance
	@Test
	public void testBaseline()
	{
		assertTrue("does not start with the expected balance", acc.getBalance() == 30000);
	}
	
	//slightly more advanced test, this test FAILS if it does NOT throw an error.
	//ergo we expect this line of code to fail.
	@Test (expected = Exception.class)
	public void testAddNegative() throws Exception
	{
		acc.deposit(-100);
	}
	
	
	//different way to test exceptions, this approach can handle multiple exceptions
	//contrary to @Test(expected = Exception.class), that can only handle one at a time
	@Test
	public void testWithdrawTooMuch()
	{
		try
		{
			//if I were a rich man.
			acc.withdraw(999999999);
			//forces the test to fail, used to check unreachable code
			fail("This piece of code was not supposed to be reachable");
		} catch (Exception e)
		{
			//this is the expected outcome
		}
	}
	

}
