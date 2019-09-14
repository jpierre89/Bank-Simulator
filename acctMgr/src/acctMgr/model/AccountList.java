package acctMgr.model;

import java.util.regex.*; 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException; 
import java.util.Scanner; 
import java.io.*;

/**
 * Maintains account list.
 * All accounts in account list have a unique id.
 * 
 * @author Jon Pierre
 * @version 1.0
 */
public class AccountList extends AbstractModel {
	private List<Account> accounts = new ArrayList<Account>(); // preserves file order
	private boolean accountsModified = false; // true iff addAccount() || removeAccount() successful. 
	private static final String NEWLINE = System.lineSeparator();
	private static final String ACCOUNTDELIMITER = " ";
	
	/** @return size of list */
	public int getSize() {return accounts.size();}
	
	/** @return true if accounts have been modified since loaded from file. */
	public boolean getAccountsModified() {return accountsModified;};
	
	/**
	 * @return sorted list of account ids; may return empty list
	 *         if no accounts exist.
	 */
	public ArrayList<Integer> getAccountIDs() {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		for (Account a : accounts) {
			ids.add(a.getID());
		}

		return ids;
	}
	
	/**
	 * @param accountID id of account to retrieve
	 * @return Account matching accountID
	 * @throws NoAccountFoundByIDException if ID not found in account list
	 */
	public Account getAccount(int accountID) throws NoAccountFoundByIDException {
		for (Account a : accounts) {
			if (a.getID() == accountID) {
				return a;
			}
		}
		throw new NoAccountFoundByIDException();
	}
	
	/**
	 * @param id int of account id
	 * @return true if found
	 */
	public boolean containsID(int id) {
		for (Account a : accounts) {
			if (a.getID() == id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param name String account name for search
	 * @return true if found
	 */
	public boolean containsName(String name) {
		for (Account a : accounts) {
			if (a.getName() == name) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param ID int id of account 
	 * @return BigDecimal account balance, or null if account not found
	 */
	public BigDecimal getBalance(int ID) {
		for (Account a : accounts) {
			if (a.getID() == ID) {
				return a.getBalance();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param ID int id of account
	 * @return String name of account, or null if account not found
	 */
	public String getNameOfAccount(int ID) {
		for (Account a : accounts) {
			if (a.getID() == ID) {
				return a.getName();
			}
		}
		return null;
	}

	/**
	 * @param accountName String name of account
	 * @param initBalance BigDecimal initial account balance
	 * @return the unique account id associated with the new account
	 */
	public int addAccount(String accountName, BigDecimal initBalance) {
		verifyAccountName(accountName);
		int uniqueID = getUniqueAccountID();
		accounts.add(new Account(accountName, uniqueID, initBalance));
		accountsModified = true;
		notifyChanged(null);
		return uniqueID;
	}

	/**
	 * @param accountID int unique account id
	 * @return true if account found and removed, false if account not found
	 */
	public boolean removeAccount(int accountID) {
		boolean accountRemoved = false;
		Iterator<Account> i = accounts.iterator();
		
		while (i.hasNext()) {
			if (i.next().getID() == accountID) {
				i.remove();
				accountRemoved = true;
				break;
			}
		}
		
		accountsModified = accountRemoved ? true : false;
		notifyChanged(null);
		return accountRemoved;
	}

	/**
	 * loads account file into account list. The current
	 * list is replaced with the accounts in the loaded file if
	 * the accounts are correctly formatted and the operation is successful. 
	 * Otherwise, exception is thrown, the current account list is unchanged
	 * and all resources used are freed.
	 * The delimiters used for account attributes in file are static AccountList attributes.
	 * 
	 * @param fileName String name of the file to read
	 * @throws FileNotFoundException iff fileName path not found
	 * @throws IllegalFileFormatException iff file is not formatted correctly
	 * @throws NoSuchElementException if if file is not formatted correctly
	 */
	@SuppressWarnings("resource")
	public void loadAccount(String fileName) 
			throws FileNotFoundException, IllegalFileFormatException, NoSuchElementException {
		
		Scanner fileScanner = null;
		Scanner lineScanner = null;
		
		try {
			fileScanner = new Scanner(new File(fileName)); 
			fileScanner.useDelimiter(NEWLINE); 
			String line;
			String accountName, accountID, accountBalance;
			int lineNumber = 0;
			List<Account> tempAccounts = new ArrayList<Account>();
			accounts.getClass();
			while (fileScanner.hasNext()) {
				lineNumber++;
				line = fileScanner.next();				
				line = line.replaceFirst(NEWLINE, "");	
				
				lineScanner = new Scanner(line);
				lineScanner.useDelimiter(ACCOUNTDELIMITER);
				
				if (!lineScanner.hasNext()) throw new IllegalFileFormatException(
						fileName, lineNumber, "Incorrect format. Name, id, or balance attribute missing.");
				accountName = lineScanner.next();
				if (!lineScanner.hasNext()) throw new IllegalFileFormatException(
						fileName, lineNumber, "Incorrect format. Name, id, or balance attribute missing.");
				accountID = lineScanner.next();
				if (!lineScanner.hasNext()) throw new IllegalFileFormatException(
						fileName, lineNumber, "Incorrect format. Name, id, or balance attribute missing.");
				accountBalance = lineScanner.next();
				
				verifyFileAccountDataFormat(
						accountName, accountID, accountBalance, fileName, lineNumber); 
				
				String validFormatAccountName = accountName;
				int validFormatAccountID = Integer.parseInt(accountID);
				BigDecimal validFormatAccountBalance = BigDecimal.valueOf(Double.parseDouble(accountBalance));
				
				Account newAccount = new Account
						(validFormatAccountName, validFormatAccountID, validFormatAccountBalance);
				
				for (Account a : tempAccounts) {
					if (a.getID() == newAccount.getID()) {
						throw new IllegalFileFormatException
							(fileName, 0, "Accounts in file do not have unique ID attributes.");
					}
				}
				
				tempAccounts.add(newAccount);
				lineScanner.close();
			}
		
		accounts = tempAccounts;	
		Account.aBalanceChanged = false;
		notifyChanged(null);
		
		}
		catch (Exception e){
			throw e;
		}
		finally {
			if (fileScanner != null)
				fileScanner.close();
			
			if (lineScanner != null) {
				lineScanner.close();
			}
		}
	}
	
	/**
	 * writes current account list to file.
	 * If the file exists it's replaced, and if the file does not exist it's created.
	 * If the accounts have not been modified since last load, the file is not written.
	 * The delimiters used for account attributes in file are static AccountList attributes.
	 * 
	 * @param fileName String name of the file to read
	 * @throws IOException if accessing file
	 * @return false if accounts have not been modified since last load
	 */
	public boolean saveAccount(String fileName) throws IOException {
		if (Account.aBalanceChanged == false && accountsModified == false)
			return false;	
		
		BufferedWriter writer = null; 
		
		try {
			writer = new BufferedWriter (new FileWriter(fileName));
			
			for (Account a : accounts) {
				writer.write(a.getName());
				writer.write(ACCOUNTDELIMITER);
				writer.write(String.valueOf(a.getID()));
				writer.write(ACCOUNTDELIMITER);
				writer.write(a.getBalance().toString());
				writer.write(NEWLINE);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (writer != null)
				writer.close();
		}
		accountsModified = false;
		Account.aBalanceChanged = false;
		return true;		
	}
	
	
	/** Verifies integrity of account attributes for valid format.
	 * String parameters should already have valid format.
	 * 
	 * @param accountID String account id
	 * @param accountName String account name
	 * @param accountBalance String account balance
	 * @throws IllegalFileFormatException if a parameter has invalid format.
	 */
	private void verifyFileAccountDataFormat(String accountName, String accountID, String accountBalance, String fileName, int lineNumber) 
		throws IllegalFileFormatException {

		String alpha = "^[a-zA-Z]+$";
		String wholeNumber = "^[0-9]+$";	
		String currency = "^[0-9]+\\.[0-9]+$";
		
		if (!Pattern.matches(alpha, accountName)) {
			throw new IllegalFileFormatException(
					fileName, lineNumber, "Account Name: " + "'" + accountName + '"' + " may only contain alphabet characters.");
		}
		
		if (!Pattern.matches(wholeNumber, accountID)) {
			throw new IllegalFileFormatException(
					fileName, lineNumber, "Account ID: " + "'" + accountID + '"' + " may only contain whole numbers.");
		}		
		
		if (!Pattern.matches(currency, accountBalance)) {
			throw new IllegalFileFormatException(
					fileName, lineNumber, "Account Balance: " + "'" + accountBalance + "'" + " must contain only whole numbers "
							+ "and a single decimal point.");
		}
	}
	
	private void verifyAccountName(String accountName) {
		String alpha = "^[a-zA-Z]+$";
		
		if (!Pattern.matches(alpha, accountName)) {
			throw new IllegalAccountNameException();
		}
	}
	
	/**
	 * @return unique account id number, not existing in list
	 */
	private int getUniqueAccountID() {
		int uniqueID = 1;
		
		while (true) {
			boolean found = false;
			for (Account account : accounts) {
				if (account.getID() == uniqueID) {
					++uniqueID;
					found = true;
					break;
				}
			}
			
			if (!found)
				return uniqueID;
		}
	}	

}
