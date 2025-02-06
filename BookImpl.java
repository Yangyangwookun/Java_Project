package Thread_project;

import java.util.Objects;
import java.util.Scanner;

public class BookImpl implements Book{
	public String title;
	public String writer;
	public String Type;
	public String publisher;
	public int price;
	
	//1,2,3 메소드 구현
	//타입에 전공책, 소설책, 만화책 여부 넣는걸로 합시다. 그게 더 간단할거 같습니다.(1월 5일자)
	 public BookImpl(String title,String writer,String Type, String publisher,int price) {
	      this.title = title;
	      this.writer = writer;
	      this.Type = Type;
	      this.publisher = publisher;
	      this.price = price;
	   }
	   void hi() {
	      System.out.println("안녕하세요 검색하신 책을 소개해 드리겠습니다.");
	   }
	   @Override
	   public void Book_inform() {
	      System.out.println(("책의 정보: \n제목: "+ title +", 작가: "+ writer + ", 종류: " + Type +", 출판사: "
	               + publisher +", 가격: " + price));
	   }
	   
	//1월 5일자
	@Override
	public String getName() {
		return this.title;
	}
	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}
	
	
	//1월 6일자
	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
		 if (obj == null || getClass() != obj.getClass()) return false;
		 BookImpl book = (BookImpl) obj;
		 return title==book.title;
	}
	@Override
	public int hashCode() {
		return Objects.hash(title);
	}
	
	
	@Override
	public void edit() {
		System.out.println("새로운 정보를 입력합니다.");
		System.out.println("기존의 정보");
		Book_inform();
		
		Scanner sc=new Scanner(System.in);
		System.out.print("제목을 입력하시오 ");
		this.title=sc.nextLine();
		System.out.print("작가를 입력하시오 ");
		this.writer=sc.nextLine();
		System.out.print("종류를 입력하시오 ");
		this.Type=sc.nextLine();
		System.out.print("출판사를 입력하시오 ");
		this.publisher=sc.nextLine();
		System.out.print("가격을 입력하시오 ");
		this.price=sc.nextInt();
		
	}

}
