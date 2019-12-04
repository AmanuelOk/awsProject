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

import gcit.springboot.project.dao.LibrarianDAO;
import gcit.springboot.project.entity.BookCopiesModel;
import gcit.springboot.project.entity.LibraryBranchModel;


@RestController
@CrossOrigin(origins="http://zeriga.s3-website.us-east-2.amazonaws.com")
public class LibrarianService {
	@Autowired
	LibrarianDAO lbdao;
	@RequestMapping(value="/listOfBranches", method=RequestMethod.GET, produces="application/json")
	public List<LibraryBranchModel> getBranchList(){
		try {
		return lbdao.getList_of_branches();}
		catch(SQLException  e) {
			return null;
		}
			
	}
	@RequestMapping(value="/insertBranch", method=RequestMethod.POST, consumes="application/json")
    @Transactional
	public String insertBranch(@RequestBody LibraryBranchModel branch) {
		if(branch!=null) {
			try {
				return "BranchId:"+""+(lbdao.insertLibrary(branch)).toString();
				
			}
			catch(SQLException | ClassNotFoundException e) {
				return "unsuccesful!";
			}
		
		}
		else return "unsuccessful!";
		
	}
	@RequestMapping(value="/remove/{branchId}", method=RequestMethod.GET)
    @Transactional
	public String removeBranch(@PathVariable int branchId) {
		try {
			lbdao.deleteBranch(branchId);
			return "Successful";
		}
		catch(SQLException e) {
			return "Unsuccessful";
		}
	}
	@RequestMapping(value="/addBookCopies", method=RequestMethod.POST, consumes="application/json")
    @Transactional
	public String addBooks(BookCopiesModel copies) {
		if(copies!=null) {
		try {
			lbdao.addBooks(copies);
			return "Successful!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "unsuccessful!";
		}}
		else return "unsuccessful!";
	}
}
