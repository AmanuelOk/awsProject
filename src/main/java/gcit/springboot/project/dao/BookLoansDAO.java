package gcit.springboot.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import gcit.springboot.project.entity.BookLoansModelExtended;
import gcit.springboot.project.entity.BorrowerModel;

@Component
public class BookLoansDAO extends BaseDAO<BookLoansModel> implements ResultSetExtractor<List<BookLoansModel>> {

	@Autowired 
	BookDAO bdao;
	@Autowired 
	BookLoansDAO bldao;
	@Autowired
	LibrarianDAO ldao;
	@Autowired
	BorrowerDAO brdao;
	public String saveBookLoan(BookLoansModel loan) throws ClassNotFoundException, SQLException {
if(loan!=null) {
if((Integer)loan.getBookId()!=null && (Integer)loan.getBranchId()!=null && (Integer)loan.getCardNo() !=null) {
			String sql="insert into tbl_book_loans values(?,?,?,curDate(),DATE_ADD(curDate(),interval 7 DAY),null)";
			mySqlTemplate.update(sql, new Object[] {loan.getBookId(),loan.getBranchId(),loan.getCardNo()});
			deductBookCopies(loan.getBranchId(),loan.getBookId());
			return "Successful Entry";}
else return "Incomplete request";
		}
		else return "Empty request!";
		
		}

	public List<BookLoansModel> showBorrowedBooks() throws ClassNotFoundException, SQLException{
		String sql= "select * from tbl_book_loans";
		return mySqlTemplate.query(sql,this);
	}
	
	
	@Override
	public List<BookLoansModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<BookLoansModel> list = new ArrayList<BookLoansModel>();
		while(rs.next()) {
			BookLoansModel loans = new BookLoansModel();
			loans.setBookId(rs.getInt("bookId"));
			loans.setBanchId(rs.getInt("branchId"));
			loans.setCardNo(rs.getInt("cardNo"));
			loans.setDateOut(rs.getString("dateOut"));
			loans.setDateIn(rs.getString("dateIn"));
			loans.setDueDate(rs.getString("dueDate"));
			list.add(loans);
		}
		return list;
	}
	
	public List<BookLoansModelExtended>getBookLoansByName() throws ClassNotFoundException, SQLException{
	List<BookLoansModel> list= showBorrowedBooks();
	List<BookLoansModelExtended> listExtended= new ArrayList<>();
	for(BookLoansModel blmd:list) {
		BookLoansModelExtended blmde= new BookLoansModelExtended();
	blmde.setDateIn(blmd.getDateIn());
	blmde.setDateOut(blmd.getDateOut());
	blmde.setDueDate(blmd.getDueDate());
	blmde.setBook(bdao.getBookById(blmd.getBookId()).get(0));
	blmde.setBranch(ldao.getBranch(blmd.getBranchId()).get(0));
	blmde.setCardNo(blmd.getCardNo());
	blmde.setBorrower(brdao.getBorrowerByCard(blmd.getCardNo()).get(0));
	listExtended.add(blmde);
	}
	return listExtended;
	}
	public void deductBookCopies(int branchId, int bookId) {
		String sql="update tbl_book_copies set noOfCopies = noOfCopies-1 where branchId=? and bookId=?";
		mySqlTemplate.update(sql, new Object[] {branchId,bookId});
	}
	public int addNewBorrower(BorrowerModel borrower) throws SQLException {
		String sql="insert into tbl_borrower (name,address,phone)values(?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		
		mySqlTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
	          ps.setString(1,borrower.getName());
	          ps.setString(2, borrower.getAddress());
	          ps.setString(3, borrower.getPhone());
	          return ps;
	        }, holder);
	        return holder.getKey().intValue();}
		
public String extendDueDate(int cardNo) throws SQLException {
	String sql="update tbl_book_loans set dueDate = DATE_ADD(curDate(),interval 7 DAY) where cardNo=?";
	mySqlTemplate.update(sql, new Object[] {cardNo});
	return "SuccesfulUpdate!";
}
}
