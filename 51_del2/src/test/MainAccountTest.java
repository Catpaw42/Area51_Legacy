package test;
import spil.*;
public class MainAccountTest {

	public static void main(String[] args) {
		Account TestAccount = new Account(3000);
		System.out.println(TestAccount.toString());
		//Test af fors�g på at sætte saldo til negativ
		try {
			TestAccount.setScore(-10);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fors�g på at sætte score til negativ fejler" + TestAccount);
		}
		//test af at sætte score til 0
		try {
			TestAccount.setScore(0);
		} catch (Exception e) {
			System.out.println("Hvis denne exception opstår, er der fejl i Account!!");
			e.printStackTrace();
		}
		//Test af at deposite negativ værdi fra
		try {
			TestAccount.deposit(-10);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Negativ deposit fejler" + TestAccount);
		}
		//test af at deposite nul 
		try {
			TestAccount.deposit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Deposit af 0 er fejlet - fejl i Account!!!" + TestAccount);
		}
		//test af normal deposit
		try {
			TestAccount.deposit(3000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Deposit af 3000 er fejlet - fejl i Account!!!" + TestAccount);
		}
		//Test af deposit hvor der vil komme int overflow.
		try {
			TestAccount.deposit(Integer.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fejler fordi maximum bel�b (MAX_VALUE) bliver overskredet" + TestAccount);
		}		
		//test af negativ withdraw
		try {
			TestAccount.withdraw(-10);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Negativ withdrawal fejler" + TestAccount);
		}
		//Test af withdraw dre overstiger saldo
		try {
			TestAccount.withdraw(3100);
		} catch (Exception e) {
			System.out.println("Fejler fordi det nye bel�b vil komme under 0" + TestAccount.getScore());
		}
		//test af withdraw til 0 i saldo
		try {
			TestAccount.withdraw(3000);
		} catch (Exception e) {
			System.out.println("Fejler fordi det nye bel�b vil komme under 0" + TestAccount.getScore());
		}
		
	}
}

