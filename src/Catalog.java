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
 * The collection class for Book objects
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Catalog implements Serializable {
  private static final long serialVersionUID = 1L;
  private List books = new LinkedList();
  private static Catalog catalog;
  /*
   * Private constructor for singleton pattern
   * 
   */
  private Catalog() {
  }
  /**
   * Supports the singleton pattern
   * 
   * @return the singleton object
   */
  public static Catalog instance() {
    if (catalog == null) {
      return (catalog = new Catalog());
    } else {
      return catalog;
    }
  }
  /**
   * Checks whether a book with a given book id exists.
   * @param bookId the id of the book
   * @return true iff the book exists
   * 
   */
  public Book search(String bookId) {
    for (Iterator iterator = books.iterator(); iterator.hasNext(); ) {
      Book book = (Book) iterator.next();
      if (book.getId().equals(bookId)) {
        return book;
      }
    }
    return null;
  }
  /**
   * Removes a book from the catalog
   * @param bookId book id
   * @return true iff book could be removed
   */
  public boolean removeBook(String bookId) {
    Book book = search(bookId);
    if (book == null) {
      return false;
    } else {
      return books.remove(book);
    }
  }
  /**
   * Inserts a book into the collection
   * @param book the book to be inserted
   * @return true iff the book could be inserted. Currently always true
   */
  public boolean insertBook(Book book) {
    books.add(book);
    return true;
  }
  /**
   * Returns an iterator to all books
   * @return iterator to the collection
   */
  public Iterator getBooks() {
    return books.iterator();
  }
  /*
   * Supports serialization
   * @param output the stream to be written to
   */
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(catalog);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  /*
   * Supports serialization
   *  @param input the stream to be read from
   */
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (catalog != null) {
        return;
      } else {
        input.defaultReadObject();
        if (catalog == null) {
          catalog = (Catalog) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      System.out.println("in Catalog readObject \n" + ioe);
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  /** String form of the collection
  * 
  */
  public String toString() {
    return books.toString();
  }
}
