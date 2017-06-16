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
import java.lang.*;
import java.io.*;
/**
 * Represents a single book
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Book implements Serializable {
  private static final long serialVersionUID = 1L;
  private String title;
  private String author;
  private String id;
  private Member borrowedBy;
  private List holds = new LinkedList();
  private Calendar dueDate;
  /**
   * Creates a book with the given id, title, and author name
   * @param title book title
   * @param author author name
   * @param id book id
   */
  public Book(String title, String author, String id) {
    this.title = title;
    this.author = author;
    this.id = id;
  }
  /**
   * Marks the book as issued to a member
   * @param member the borrower
   * @return true iff the book could be issued. True currently
   */
  public boolean issue(Member member) {
    borrowedBy = member;
    dueDate = new GregorianCalendar();
    dueDate.setTimeInMillis(System.currentTimeMillis());
    dueDate.add(Calendar.MONTH, 1);
    return true;
  }
  /**
   * Marks the book as returned
   * @return The member who had borrowed the book
   */
  public Member returnBook() {
    if (borrowedBy == null) {
      return null;
    } else {
      Member borrower = borrowedBy;
      borrowedBy = null;
      return borrower;
    }
  }
  /**
   * Renews the book 
   * @param member who wants to renew the book
   * @return true iff the book could be renewed
   */
  public boolean renew(Member member) {
    if (hasHold()) {
      return false;
    }
    if ((member.getId()).equals(borrowedBy.getId())) {
      return (issue(member));
    }
    return false;
  }
  /**
   * Adds one more hold to the book
   * @param hold the new hold on the book
   */
  public void placeHold(Hold hold) {
    holds.add(hold);
  }
  /**
   * Removes hold for a specific member
   * @param memberId whose hold has to be removed
   * @return true iff the hold could be removed
   */
  public boolean removeHold(String memberId) {
    for (ListIterator iterator = holds.listIterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      String id = hold.getMember().getId();
      if (id.equals(memberId)) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }
  /**
   * Returns a valid hold
   * @return the next valid hold
   */
  public Hold getNextHold() {
    for (ListIterator iterator = holds.listIterator(); iterator.hasNext(); ) {
      Hold hold = (Hold) iterator.next();
      iterator.remove();
      if (hold.isValid()) {
        return hold;
      }
    }
    return null;
  }
  /**
   * Checks whether there is a hold on this book
   * @return true iff there is a hold
   */
  public boolean hasHold() {
    ListIterator iterator = holds.listIterator();
    if (iterator.hasNext()) {
      return true;
    }
    return false;
  }
  /**
   * Returns an iterator for the holds
   * @return iterator for the holds on the book
   */
  public Iterator getHolds() {
    return holds.iterator();
  }
  /**
   * Getter for author
   * @return author name
   */
  public String getAuthor() {
    return author;
  }
  /**
   * getter for title
   * @return title of the book
   */
  public String getTitle() {
    return title;
  }
  /**
   * Getter for id
   * @return id of the book
   */
  public String getId() {
    return id;
  }
  /**
   * Getter for borrower
   * @return the member who borrowed the book
   */
  public Member getBorrower() {
    return borrowedBy;
  }
  /**
   * Getter for due date
   * @return the date on which the book is due
   */
  public String getDueDate() {
      return (dueDate.getTime().toString());
  }
  /** 
   * String form of the book
  * 
  */
  public String toString() {
    return "title " + title + " author " + author + " id " + id + " borrowed by " + borrowedBy;
  }
}