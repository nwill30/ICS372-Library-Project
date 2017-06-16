/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.util.*;
import java.io.*;
public class Member implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private static final String MEMBER_STRING = "M";
  private List booksBorrowed = new LinkedList();
  private List booksOnHold = new LinkedList();
  private List transactions = new LinkedList();
  /**
   * Represents a single member
   * @param name name of the member
   * @param address address of the member
   * @param phone phone number of the member
   */
  public  Member (String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    id = MEMBER_STRING + (MemberIdServer.instance()).getId();
  }
  /**
   * Stores the book as issued to the member
   * @param book the book to be issued
   * @return true iff the book could be marked as issued. always true currently 
   */
  public boolean issue(Book book) {
    if (booksBorrowed.add(book)) {
      transactions.add(new Transaction ("Book issued ", book.getTitle()));
      return true;
    }
    return false;
  }
  /**
   * Marks the book as not issued to the member
   * @param book the book to be returned
   * @return true iff the book could be marked as marked as returned 
   */
  public boolean returnBook(Book book) {
    if ( booksBorrowed.remove(book)){
      transactions.add(new Transaction ("Book returned ", book.getTitle()));
      return true;
    }
    return false;
  }
  /**
   * Marks the book as renewe
   * @param book the book to be renewed
   * @return true iff the book could be renewed
   */
  public boolean renew(Book book) {
    for (ListIterator iterator = booksBorrowed.listIterator(); iterator.hasNext(); ) {
      Book aBook = (Book) iterator.next();
      String id = aBook.getId();
      if (id.equals(book.getId())) {
        transactions.add(new Transaction ("Book renewed ",  book.getTitle()));
        return true;
      }
    }
    return false;
  }
  /**
   * Gets an iterator to the issued books
   * @return Iterator to the collection of issued books
   */
  public Iterator getBooksIssued() {
    return (booksBorrowed.listIterator());
  }
  /**
   * Places a hold for the book
   * @param hold the book to be placed a hold
   */
  public void placeHold(Hold hold) {
    transactions.add(new Transaction ("Hold Placed ", hold.getBook().getTitle()));
    booksOnHold.add(hold);
  }
  /**
   * Removes a hold
   * @param bookId the book id for removing a hold
   * @return true iff the hold could be removed
   */
  public boolean removeHold(String bookId) {
    for (ListIterator iterator = booksOnHold.listIterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      String id = hold.getBook().getId();
      if (id.equals(bookId)) {
        transactions.add(new Transaction ("Hold Removed ", hold.getBook().getTitle()));
        iterator.remove();
        return true;
      }
    }
    return false;
  }
  /**
   * Gets an iterator to a collection of selected ransactions
   * @param date the date for which the transactions have to be retrieved
   * @return the iterator to the collection
   */
  public Iterator getTransactions(Calendar date) {
    List result = new LinkedList();
    for (Iterator iterator = transactions.iterator(); iterator.hasNext(); ) {
      Transaction transaction = (Transaction) iterator.next();
      if (transaction.onDate(date)) {
        result.add(transaction);
      }
    }
    return (result.iterator());
  }
  /**
   * Getter for name
   * @return member name
   */
  public String getName() {
    return name;
  }
  /**
   * Getter for phone number
   * @return phone number
   */
  public String getPhone() {
    return phone;
  }
  /**
   * Getter for address
   * @return member address
   */
  public String getAddress() {
    return address;
  }
  /**
   * Getter for id
   * @return member id
   */
  public String getId() {
    return id;
  }
  /**
   * Setter for name
   * @param newName member's new name
   */
  public void setName(String newName) {
    name = newName;
  }
  /**
   * Setter for address
   * @param newName member's new address
   */
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  /**
   * Setter for phone
   * @param newName member's new phone
   */
  public void setPhone(String newPhone) {
    phone = newPhone;
  }
  /**
   * Checks whether the member is equal to the one with the given id
   * @param id of the member who should be compared
   * @return true iff the member ids match
   */
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  /** 
   * String form of the member
  * 
  */
 @Override
  public String toString() {
    String string = "Member name " + name + " address " + address + " id " + id + "phone " + phone;
    string += " borrowed: [";
    for (Iterator iterator = booksBorrowed.iterator(); iterator.hasNext(); ) {
      Book book = (Book) iterator.next();
      string += " " + book.getTitle();
    }
    string += "] holds: [";
    for (Iterator iterator = booksOnHold.iterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      string += " " + hold.getBook().getTitle();
    }
    string += "] transactions: [";
    for (Iterator iterator = transactions.iterator(); iterator.hasNext(); ) {
      string += (Transaction) iterator.next();
    }
    string += "]";
    return string;
  }
}