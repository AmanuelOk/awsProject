package gcit.springboot.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import gcit.springboot.project.entity.BookCopiesModel;
import gcit.springboot.project.entity.BookCopiesModelExtended;

@Repository
public class BookCopiesDAO extends BaseDAO<BookCopiesModel> implements ResultSetExtractor<List<BookCopiesModel>>{ 
	    @Autowired
		BookDAO bdao;
	    @Autowired
	    LibrarianDAO lbdao;

public List<BookCopiesModel> getNumberOfCopies(){
	return mySqlTemplate.query("select bookId, branchId, noOfCopies from tbl_book_copies where noOfCopies is not null", this);
}	

@Override
public List<BookCopiesModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<BookCopiesModel> list = new ArrayList<>();
		while (rs.next()) {
			BookCopiesModel b = new BookCopiesModel();
			b.setBookId(rs.getInt("bookId"));
			b.setBranchId(rs.getInt("branchId"));
			b.setNo_of_copies(rs.getInt("noOfCopies"));
			list.add(b);
		}
		return list;
	
}
public List<BookCopiesModelExtended> borrowerList() throws ClassNotFoundException, SQLException{
	List<BookCopiesModel> list= getNumberOfCopies();
	List<BookCopiesModelExtended> listExtended= new ArrayList<>();
	for(BookCopiesModel bcm:list) {
		BookCopiesModelExtended bcmde= new BookCopiesModelExtended();
	bcmde.setNo_of_copies(bcm.getNo_of_copies());
	bcmde.setBookId(bcm.getBookId());
	bcmde.setBranchId(bcm.getBranchId());
	bcmde.setBranchName(lbdao.getBranch(bcm.getBranchId()).get(0).getName());
	bcmde.setBranchAddress(lbdao.getBranch(bcm.getBranchId()).get(0).getAddress());
	bcmde.setTitle(bdao.getBookById(bcm.getBookId()).get(0).getTitle());
	listExtended.add(bcmde);
	}
	return listExtended;


}
}
