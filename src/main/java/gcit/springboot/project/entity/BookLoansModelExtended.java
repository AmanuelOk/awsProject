package gcit.springboot.project.entity;

public class BookLoansModelExtended {
	public BookModel getBook() {
		return book;
	}
	public void setBook(BookModel book) {
		this.book = book;
	}
	public BorrowerModel getBorrower() {
		return borrower;
	}
	public void setBorrower(BorrowerModel borrower) {
		this.borrower = borrower;
	}
	public LibraryBranchModel getBranch() {
		return branch;
	}
	public void setBranch(LibraryBranchModel branch) {
		this.branch = branch;
	}
	public String getDateOut() {
		return dateOut;
	}
	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getDateIn() {
		return dateIn;
	}
	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}
	private BookModel book;
	private BorrowerModel borrower;
	private LibraryBranchModel branch;
	private String dateOut;
	private String dueDate;
	private String dateIn;
	private int cardNo;
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
}
