package Thread_project;

public class Major_book extends BookImpl{
	public Major_book(String title, String writer, String Type, String publisher, int price, String major) {
		super(title, writer, Type, publisher, price);
		this.major=major;
		// TODO Auto-generated constructor stub
	}
	public String major;
	//오버라이딩
	
	//책의 정보 출력(전공 책 버전)
	@Override
	   public void Book_inform() {
	      System.out.println(("책의 정보: \n제목: "+ title +", 작가: "+ writer + ", 종류: " + Type +", 출판사: "
	               + publisher +", 가격: " + price + ", 전공: " + major));
	   }
}
