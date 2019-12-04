package gcit.springboot.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import gcit.springboot.project.entity.BorrowerModel;

@Repository
public class BorrowerDAO extends BaseDAO<BorrowerModel> implements ResultSetExtractor<List<BorrowerModel>>{

	
	public List<BorrowerModel> getBorrowerByCard(int cardNo){
		return mySqlTemplate.query("select name,address,phone from tbl_borrower where cardNo=?", new Object[] {cardNo}, this);
	}
	
	
	
	public List<BorrowerModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<BorrowerModel> list = new ArrayList<>();
		while (rs.next()) {
			BorrowerModel b = new BorrowerModel();
			b.setName(rs.getString("name"));
			b.setPhone(rs.getString("phone"));
			b.setAddress(rs.getString("address"));
			list.add(b);
		}
		return list;
	}
	}


