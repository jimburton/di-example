# Dependency Injection

This repository contains the lab exercise for Week 4 in CI346, and is
all about the *Dependency Injection* design pattern. Although the program
currently works, it violates some or all of the SOLID principles, and
is unfortunately brittle -- making a small change, such as printing
the information of a book in a different style, would require many
changes. Your task is to refactor the code so that it follows the
SOLID principles and is easier to maintain and enhance.

  
The application reads in a text file containing a book, prints out the
contents of the book in various ways, and can save and retrieve books
as Java objects. The entry point is the `main` method in the class
`CI346.week4.Main`. Run the `main` method and study the code of all
classes until you understand the application.
  
The `Book` class contains methods for printing the details of books,
such as `printContents`. Which SOLID principles does this
violate?

## Improving things
  
There are several ways we could begin to improve the `Book` class. We
could create interfaces that specify the methods that
printable/formattable objects should implement. However, since all
formattable book objects will share some behavior, we will use
inheritance instead. So, you are going to create a series of classes
that specialise in formatting `Book` objects. In the package
`CI346.books`, create a class called `BookFormatter`
with the following contents (you can download this file from
studentcentral):


    public abstract class BookFormatter {

      protected final Book book;

      public BookFormatter(Book book) {
        this.book = book;
      }

      public abstract String format();

      public String formatInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(book.getTitle()+" ["+book.getLength()+" pages]");
        sb.append("\n");
        sb.append(book.getAuthor().getName());
        sb.append("\n");
        return sb.toString();
      }
    
      public String formatHeader() {
        return book.getAuthor().getName()+", "+book.getTitle();
      }
    
      public String formatPage() {
        return book.getCurrentPage();
      }
  }

`BookFormatter` is an *abstract* class, so cannot be instantiated
directly. It is meant to act as the *superclass* for other
classes that will handle the formatting of books. Note that the
`BookFormatter` constructor requires a book (the one to be
formatted). The `format` method is *abstract*, so child
classes will have to provide their own implementation, whilst a
default implementation has been provided for the other methods. Of
course, if child classes want to provide a different implementation of
any of those methods they could override them. So,
`BookFormatter` is *open for extension, closed for
modification*.

The other thing we've improved is that, while the `Book` class
prints everything to standard output using
`System.out.println`, `BookFormatter` just prepares the
`String` to be printed, without knowing anything about
*how* to print it. Thus, we could use this formatter to print to
standard output, to a log file, or anywhere we like.

## Formatting books
  
In the same package, create a class called `BookContentsFormatter`
that extends `BookFormatter`. Create a constructor for the new
class that calls the constructor of the parent class:

    public BookContentsFormatter(Book book) {
      super(book);
    }

`BookContentsFormatter` needs to override the abstract method
from its parent class. The `format` method should produce a
`String` which is the same as that printed by the `for`
loop in the `main` method. `BookContentsFormatter` can
be supplied wherever its parent class is expected (*Liskov
Substitution Principle*).
  
Replace the contents of the `if` statement in the `main` method with the following code:

    b = anotherBook.get();
    BookFormatter bf = new BookContentsFormatter(b);
    System.out.println(bf.format());

You can now remove all of the `print` methods from the
`Book` class. This class now contains no code relating to
formatting or printing information. How does this relate to the SOLID
principles?

## Formatting books differently
  
Create another formatter, `BookInfoFormatter`. Base this class
on `BookContentsFormatter` except that the `format`
method simply returns the result of calling the `formatInfo`
method.

Alter the `main` method so that it uses a
`BookInfoFormatter` instead of the
`BookContentsFormatter`.

## Interface segregation
  
Another problem with this application is that the `Book` class
contains code relating to saving and retrieving objects. If we decide
to start storing `Book` objects in a different way (e.g. by
saving them in a database, or writing a `JSON` object to a
file), we would need to keep making changes to the `Book`
class, which would eventually contain a lot of code that was nothing
to do with books.

You are going to move some of this persistence-related code into an
interface in order to support the *Interface Segregation
Principle*.

In the package `CI346.books`, create an interface called
`BookPersister`. This interface should extend the
`Serializable` interface. Move the `read` method from
the `Book` class to the new interface (note this is only
possible because `read` is a `static` method). Declare
the `read` method in the interface but don't define it. That
is, the interface should contain this line: 

    public void save(); 

which will force all classes which implement the
`BookPersister` interface to provide an implementation of the
`save` method.
  
Change the `Book` class so that it implements the
`BookPersister` interface instead of `Serializable`. Now
change the line in the `main` method that reads in the
`Book` object to use the new interface: 

    Optional<Book> anotherBook = BookPersister.read(path);
  
Note that the `Book` object is not entirely separate from
concerns of saving or retrieving objects, because it has to implement
the `save` method from the `BookPersister` interface. 
  
A final problem with the design of this application is the fact that
the `Author` class contains code relating to formatting and
printing the details of authors. Bearing in mind what you've done so
far, how would you improve this situation? 
