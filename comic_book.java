package Thread_project;

public class comic_book extends BookImpl{
	
	public comic_book(String title, String writer, String Type, String publisher, int price, int picture_num) {
		super(title, writer, Type, publisher, price);
		this.picture_num=picture_num;
		// TODO Auto-generated constructor stub
	}
	public int picture_num;
	//오버라이딩
	//책의 정보 출력(만화 책 버전)
	@Override
	   public void Book_inform() {
	      System.out.println(("책의 정보: \n제목: "+ title +", 작가: "+ writer + ", 종류: " + Type +", 출판사: "
	               + publisher +", 가격: " + price + ", 그림 수: " + picture_num));
	   }
	
}
