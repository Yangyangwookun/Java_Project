package Thread_project;

public class novel_book extends BookImpl{
	public novel_book(String title, String writer, String Type, String publisher, int price, String genre) {
		super(title, writer, Type, publisher, price);
		this.genre=genre;
		// TODO Auto-generated constructor stub
	}
	public String genre;
	//오버라이딩
	
	//책의 정보 출력(소설 책 버전)
	@Override
	   public void Book_inform() {
	      System.out.println(("책의 정보: \n제목: "+ title +", 작가: "+ writer + ", 종류: " + Type +", 출판사: "
	               + publisher +", 가격: " + price +", 장르: " + genre));
	   }
}
