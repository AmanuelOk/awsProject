package gcit.springboot.project;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gcit.springboot.project.dao.AuthorDAO;
import gcit.springboot.project.dao.BookCopiesDAO;
import gcit.springboot.project.dao.BookDAO;
import gcit.springboot.project.dao.BookLoansDAO;
import gcit.springboot.project.dao.BookLoansModel;
import gcit.springboot.project.dao.BorrowerDAO;
import gcit.springboot.project.entity.AuthorsModel;
import gcit.springboot.project.entity.BookCopiesModelExtended;
import gcit.springboot.project.entity.BookModel;

@RestController
@CrossOrigin(origins="http://zeriga.s3-website.us-east-2.amazonaws.com")
class BorrowerService {
	@Autowired
	BookCopiesDAO bcdao;
	@Autowired
	BookDAO bdao;
	@Autowired
	AuthorDAO adao;
	@Autowired 
	BookLoansDAO bldao;
	@Autowired
	BorrowerDAO brdao;
	@RequestMapping(value="/client/readAllBooks", method=RequestMethod.GET, produces="application/json")
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
		}}
	@RequestMapping(value="/client/readAllAuthors", method=RequestMethod.GET, produces="application/json")
	public List<AuthorsModel> readAllAuthors() throws ClassNotFoundException, SQLException{
		try {
			return adao.readAllAuthors();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}}
		
	
	
	@RequestMapping(value="/client/borrow", method=RequestMethod.POST, consumes="application/json")
	@Transactional
	public String borrow(@RequestBody BookLoansModel loan) {
		try {
			return bldao.saveBookLoan(loan);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "Class Cast Exception";
		} catch (SQLException e) {
			e.printStackTrace();
			return "CheckSQL syntax";
		}
		
	}
	
	@RequestMapping(value="/client/showBookList", method=RequestMethod.GET, produces="application/json")
	public List<BookCopiesModelExtended> listAllBooksByName(){
		try {
			return bcdao.borrowerList();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	@RequestMapping(value="/extend/{cardNo}", method=RequestMethod.POST)
	@Transactional
	public String borrow(@PathVariable int cardNo) {
		try {
		   bldao.extendDueDate(cardNo);
			return "successfully Extended";
		
		} catch (SQLException e) {
			e.printStackTrace();
			return "CheckSQL syntax";
		}
		
	}
	
	
	
}
