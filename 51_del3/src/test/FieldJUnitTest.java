package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import spil.Decorator;
import spil.DiceCup;
import spil.Player;
import spil.fields.*;

public class FieldJUnitTest
{
	static DiceCup dc;
	static Field[] fields;
	Player p;
	Player p2;

	//create a new "board" for the test
	@BeforeClass
	public static void doBeforeClass()
	{
		dc = new DiceCup();
		fields = new Field[7];

		fields[0] = new Territory("Encampment"	, 1000, 100);
		fields[1] = new LaborCamp("Huts"		, 2500, 100, dc);
		fields[2] = new Fleet("TheBuccaneers"	, 4000, 250);
		fields[3] = new Fleet("PrivateerArmada"	, 4000, 250);
		fields[4] = new Refuge("WalledCity"		, 5000);
		fields[5] = new Tax("GoldMine"			, 2000);
		fields[6] = new Tax("Caravan"			, 4000, 10);
		
		Decorator.setupGUI(fields);
	}

	@AfterClass
	public static void doAfterClass()
	{

	}
	//used to reset the players between tests
	@Before
	public void doBefore()
	{
		p = new Player("Dummy", 30000);
		p2 = new Player("MCDuck", 30000);
		dc.rollDice();
	}

	@After
	public void doAfter()
	{

	}

	@Test
	public void testTerritory()
	{
		//check that player p, and the field exists.
		assertNotNull(p);
		assertNotNull(fields[0]);
		//first test land on your own field
		Ownable f = (Ownable) fields[0];
		f.setOwner(p); 												//makes p the owner
		f.landOnField(p); 											//lands on the field
		//assert player unchanged by checking account and active
		assertTrue(p.getAccount().getBalance() == 30000); 			// check that nothing has changed
		assertTrue(p.getActive());									// check that nothing has changed
		//assert field unchanged by checking owner
		assertTrue(f.getOwner() == p);								//check that p is still the owner
		
		//second test landing on another players field
		doBefore();													//reset players
		f.setOwner(p2);												//set player 2 as owner
		f.landOnField(p); //no GUI output from this.
		
		//assert that p has lost the fields rent of 100
		assertTrue(p.getAccount().getBalance() == 30000 - 100);
		
		//assert that p2 has gained this
		assertTrue(p2.getAccount().getBalance() == 30000 + 100);
		
		//assert that all actors are otherwise unchanged
		assertTrue(p.getActive());
		assertTrue(p2.getActive());
		assertTrue(f.getOwner() == p2);
		
		//finally test landing on a free field
		doBefore();													//reset players
		f.setOwner(null);											//clear the owner from field
		assertTrue(f.getOwner() == null);
		f.landOnField(p);
		
		//this is set up circumvent the problem that this option needs user input.
		if(f.getOwner() == p) //if you select to buy the field
		{
			//assert balance and owner now changed
			assertTrue(p.getAccount().getBalance() == 30000 - f.getPrice());
			assertTrue(f.getOwner() == p);
		}
		else //if you select not to buy the field
		{
			//assert owner and balance unchanged
			assertTrue(p.getAccount().getBalance() == 30000);
			assertTrue(f.getOwner() == null);
		}
	}
	
	@Test
	public void testLaborCamp()
	{
		//test landing on another players field with enough money
		Ownable f = (Ownable) fields[1];							//cast to Ownable
		f.setOwner(p2);												//set player p2 as owner
		f.landOnField(p);											//p lands on p2's field
		//check that money was moved equal to: baserent(100) * diceroll
		assertTrue(p.getAccount().getBalance() == 30000 - 100 * dc.getSum());
		assertTrue(p2.getAccount().getBalance() == 30000 + 100 * dc.getSum());
		//did p go broke? he's not supposed to go broke
		assertTrue(p.getActive());
		//check that owner is still the same. (p2)
		assertTrue(f.getOwner() == p2);
	}
	
	@Test
	public void testFleet()
	{
		Ownable f = (Ownable) fields[2];							//setup f
		Ownable f2 = (Ownable) fields[3];							//setup f2
		assertNotNull(f);											//check f exists
		assertNotNull(f2);											//check f2 exists
		//test landing on another persons fleet
		f.setOwner(p2);												//set p2 as owner of f
		assertTrue(p2.getNumberOfFleets() == 1);					//check that he only owns one
		f.landOnField(p);											//p lands on p2's fleet
		
		//check that money was moved
		assertTrue(p.getAccount().getBalance() == 30000 - ((int)(Math.pow(2, 1) * 250)));
		assertTrue(p2.getAccount().getBalance() == 30000 + ((int)(Math.pow(2, 1) * 250)));
		
		//test buying another fleet (will loop till you press the right button)
		doBefore();
		f.setOwner(p2);
		assertTrue(f2.getOwner() == null);
		while(f2.getOwner() == null)
		{
			f2.landOnField(p2); //if you press no, this should do nothing
		}
		//now test that number of fleets is 2
		assertTrue(f2.getOwner() == p2);
		assertTrue(p2.getNumberOfFleets() == 2);
		//check that f2 paid for the fleet-field, also shows that extra loops(above) does nothing
		assertTrue(p2.getAccount().getBalance() == 30000 - f2.getPrice());
		//test landing on a fleet owned by a player with more than one fleet
		f.landOnField(p);
		
		//check that money was moved equal to (2^2)*250
		assertTrue(p.getAccount().getBalance() == 30000 - ((int)(Math.pow(2, 2) * 250)));
		assertTrue(p2.getAccount().getBalance() == 30000 - f2.getPrice() + ((int)(Math.pow(2, 2) * 250)));
		
		
		//test landing on a fleet and going broke
		doBefore();												//reset players
		f.setOwner(p2);
		try
		{
			p.getAccount().setBalance(1);						//set player p's balance to 1
		}
		catch (Exception e)
		{
			fail("not supposed to happen");
		}
		assertTrue(p.getAccount().getBalance() == 1);
		assertTrue(f.getOwner() == p2);
		f.landOnField(p);
		
		//check that money ($1) was moved
		assertTrue(p.getAccount().getBalance() == 0);
		assertTrue(p2.getAccount().getBalance() == 30000 + 1);
		//check that player p is inactive
		assertFalse(p.getActive());
	}
	
	@Test
	public void testRefuge()
	{
		//test landing on refuge
		Field f = fields[4];
		assertNotNull(f);
		f.landOnField(p);
		assertTrue(p.getAccount().getBalance() == 30000 + 5000);				//check that he received 5000
		assertTrue(p.getActive());
		
	}
	
	@Test
	public void testTax()
	{
		//test landing on tax and paying a static price
		Field f = fields[5];
		assertNotNull(f);
		f.landOnField(p);
		assertTrue(p.getAccount().getBalance() == 30000 - 2000);
		assertTrue(p.getActive());
		//test landing on tax and choosing between two options
		doBefore();
		f = fields[6];
		assertNotNull(f);
		assertTrue(p.getAccount().getBalance() == 30000);
		f.landOnField(p);
		//check that p payed either 4000 or 10% (3000)
		assertTrue(p.getAccount().getBalance() == (30000 - 4000) || p.getAccount().getBalance() == 30000 - 3000);
		assertTrue(p.getActive());
	}
	
	
}
