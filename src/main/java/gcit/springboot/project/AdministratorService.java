package gcit.springboot.project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gcit.springboot.project.dao.AuthorDAO;
import gcit.springboot.project.dao.BookDAO;
import gcit.springboot.project.dao.BookLoansDAO;
import gcit.springboot.project.entity.AuthorsModel;
import gcit.springboot.project.entity.BookLoansModelExtended;
import gcit.springboot.project.entity.BookModel;
import gcit.springboot.project.entity.Book_Author_Genre_Branch_CopiesModel;
import gcit.springboot.project.entity.BorrowerModel;

@RestController

@CrossOrigin(origins="http://zeriga.s3-website.us-east-2.amazonaws.com")
public class AdministratorService {

	@Autowired
	AuthorDAO adao;

	@Autowired
	BookDAO bdao;
	
	@Autowired
	BookLoansDAO bldao;

	@Transactional
	@RequestMapping(value="/saveAuthor", method=RequestMethod.POST, consumes="application/json")
	public String saveAuthor(@RequestBody AuthorsModel author){
		try {
			if(author!=null){
				if(author.getAuthorName()!=null){
					adao.saveAuthor(author);
					return "Author updated sucessfully";
				}}
			else return "unsuccessful";}
		catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return "unsuccessful";
		} return "unsuccessful";}


	@Transactional
	@RequestMapping(value="/saveAllBookRelations", method=RequestMethod.POST, consumes="application/json")
	public String saveBook(@RequestBody Book_Author_Genre_Branch_CopiesModel book){
		if(book!=null) {
			try {
			Integer bookId = (Integer)bdao.saveBookWithID(book);
			bdao.saveBookByAuthorId(book, bookId);
			bdao.saveBookByGenreId(book, bookId);
			bdao.saveBookByNoOfCopies(book, bookId);
		   return "Book successfully Added";}
			catch(SQLException | ClassNotFoundException e) {
				if((Integer)book.getAuthorId()!=null)return "AuthorId Can't be null";
				else if((Integer)book.getGenreId()!=null)return "GenreId Can't be null";
				else if((Integer)book.getBranchId()!=null)return "BranchId Can't be null";
				else if((Integer)book.getCopies()!=null)return "No_of_copies Can't be null";
				else return "Failed!";
			}}
			else return "Empty request!";}
			
	

	@RequestMapping(value="/readAuthors/{searchString}", method=RequestMethod.GET, produces="application/json")
	public List<AuthorsModel> readAuthors(@PathVariable String searchString) throws SQLException{
		List<AuthorsModel> authors = new ArrayList<>();
		try {
			if(searchString!=null){
				authors= adao.readAllAuthorsByName(searchString);
			}else{
				authors = adao.readAllAuthors();

			}
			for(AuthorsModel a: authors){
				a.setBook_list(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return authors;

	}

	@RequestMapping(value="/readAuthorsByName", method=RequestMethod.GET, produces="application/json")
	public List<AuthorsModel> readAuthorsByName(@RequestParam String searchString) throws SQLException{
		List<AuthorsModel> authors = new ArrayList<>();
		try {
			if(searchString!=null){
				authors= adao.readAllAuthorsByName(searchString);
			}else{
				authors = adao.readAllAuthors();
			}
			for(AuthorsModel a: authors){
				a.setBook_list(bdao.readAllBooksByAuthorId(a.getAuthorId()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return authors;

	}
	@RequestMapping(value="/readAllBooks", method=RequestMethod.GET, produces="application/json")
public List<BookModel> readAllBooks(){
	try {
		return bdao.readAllBooks();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		return null;	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
		
		
	}
	
	@RequestMapping(value="/updateBook", method=RequestMethod.POST, consumes="application/json")
	@Transactional
	public String updateBook(@RequestBody BookModel book) {
		try {
			if((Integer)book.getBookId()!=null) 
			bdao.updateBook(book);
			return "Succcessful Operation!";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Sql failure";
		}
		
	}
	@RequestMapping(value="/readAllAuthors", method=RequestMethod.GET, produces="application/json")
	public List<AuthorsModel> readAllAuthors(){
		try {
			return adao.readAllAuthors();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}
	@RequestMapping(value="/addNewClient", method=RequestMethod.POST, consumes="application/json")
	@Transactional
	public int addNewBorrower(@RequestBody BorrowerModel borrower) {
		try {
			if(borrower.getName()!=null && borrower.getAddress()!=null) 
			{  return bldao.addNewBorrower(borrower);}
			else {return -1;}
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	@RequestMapping(value="/showBookLoans", method=RequestMethod.GET, produces="application/json")
	public List<BookLoansModelExtended> readAllBorrowedBooks(){
		try {	
		return bldao.getBookLoansByName();}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		catch(ClassNotFoundException e) {
			e.getStackTrace();
			return null;
		}
		
	}
	@RequestMapping(value="/updateDueDate/{cardNo}", method=RequestMethod.GET)
	@Transactional
	public String extendDueDate(@PathVariable int cardNo) {
		if((Integer)cardNo!=null) {
			try {
			return bldao.extendDueDate(cardNo);}
			catch(SQLException e) {
				return "unsuccessful!";
			}
		}
		return "unsuccesfull!";
	}
	
	@RequestMapping(value="/deleteaAuthor", method=RequestMethod.POST, consumes="application/json")
	@Transactional
	public String deleteAuthor(@RequestBody AuthorsModel author) {
		try {
			if(author.getAuthorName()!=null && (Integer)author.getAuthorId()!=null) 
			{ adao.deleteAuthor(author);
			       return author.getAuthorName()+">> "+"Deleted"; }
			else {return "unsuccessful!";}
		}
		 catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unsuccessful!";
		}
		
	}
	
}