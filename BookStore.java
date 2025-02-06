package Thread_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookStore {
	//컬랙션 활용
	public String name;
	// 여기에 저장을 만화책, 소설책, 전공책 이렇게 BookImpl 자식 클래스들로 하지만 저장은 전부 BookImpl 클래스로 될것이다.
	// 그러면 BookImpl 타입에 소설, 전공, 만화 여부를 정해주고 객체 초기 생성 할 때 해당 클래스만의 메소드를 이용하자
	public static HashMap<BookImpl,Integer> stock;
	public int money;
	public String owner_name;
	public static ArrayList<Consumer> blacklist;
	
	//book에 속하는 클래스들을 입력으로 받아서,재고등록 메소드(T book){if (T instanceof book)else{}}
	//1.재고 등록(제네릭 활용)(입력: 책, 반환값:x)
	public <E> void RegisterBook(E book){
		try {
			
			BookImpl input=null;
			Scanner sc=new Scanner(System.in);
			if(book instanceof BookImpl) {
				System.out.println("일반 책을 등록합니다.");
				input=(BookImpl)book;
			
			}else if(book instanceof novel_book){
				System.out.println("소설 책을 등록합니다.");
				input=(novel_book)book;
			
			}else if(book instanceof Major_book){
				System.out.println("전공 책을 등록합니다.");
				input=(Major_book)book;
			
			}else if(book instanceof comic_book){
				System.out.println("만화 책을 등록합니다.");
				input=(comic_book)book;
			
			}else {
				System.out.println("부적절한 입력입니다.");
			}
		
			if(SearchBook(input)==null) {
				System.out.println("신규 등록을 합니다.");
				System.out.print("책의 수를 입력하시오: ");
				int num=sc.nextInt();
				System.out.println("정상등록 되었습니다.");
				stock.put(input, num);
			}else {
				System.out.println("이미 존재하는 책 입니다.");
			}
			
		}catch (Exception e) {
			System.out.println("재고 등록 과정에서 에러가 발생하였습니다.");
		}finally {
			System.out.println("재고 등록을 종료합니다.");
		}
		
	}
	
	//2.재고 정보 수정(입력: 책, 반환값:x)
	public void UpdateStock(BookImpl book) {
		Scanner sc=new Scanner(System.in);
			try {
				BookImpl updatebook=SearchBook(book);
				if(updatebook==null) {
					System.out.println("해당 책은 재고에 없습니다.");
				}
				if(updatebook!=null) {
					
					System.out.println("책의 정보를 수정합니다");
					System.out.print("업데이트할 책의 수를 입력하시오(ex.-6,3): ");
					int num=sc.nextInt();
					int currentnum=stock.get(updatebook);
					System.out.println("현재 해당 책은 "+currentnum+"권 있습니다.");
					int updatenum=currentnum+num;
					System.out.println();
					if(updatenum>0) {
						stock.replace(updatebook, updatenum);
						System.out.println("재고가 정상적으로 수정되었습니다.");
					}else if(updatenum==0){
						stock.remove(updatebook);
						System.out.println("재고가 0이되어 삭제되었습니다.");
					}else {
						System.out.println("재고가 음수가 되어 실행되지 않았습니다.");
					}
					
			}
		}catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
		}
	}
	
	//3.소유한 책 정보 출력(단순 stock 값 출력)
	public void PrintStock() {
		System.out.println(name+"서점이 소유하고 있는 책 정보 입니다.");
		for(BookImpl book:stock.keySet()) {
			int booknumber=stock.get(book);
			System.out.print("제목: "+book.title+", 작가: "+book.writer+", 종류: "
			+book.Type+", 출판사: "+book.publisher+", 가격: "+book.price);
			switch (book.Type){
			case "소설":
				novel_book novel=(novel_book)book;
				System.out.print(", 장르: "+novel.genre);
				break;
			case "전공":
				Major_book major=(Major_book)book;
				System.out.print(", 전공: "+major.major);
				break;
			case "만화":
				comic_book comic=(comic_book)book;
				System.out.print(", 그림 수: "+comic.picture_num);
				break;
			default:
				
				break;
			}
			System.out.println(", 책의 수: "+booknumber);
			System.out.println();
		}
	}
	
	//4.책 환불 처리(입력: 책, 반환값:true/false)
	public boolean RefundBook(BookImpl refundbook) {
		BookImpl refund=SearchBook(refundbook);
		
		//환불하려는 책이 창고에 있다면,소지한 돈이 충분하다면
		if(refund!=null&&money>=refund.price) {
			stock.replace(refund, stock.get(refund)+1);
			this.money-=refund.price;
			System.out.println("환불이 정상처리 되었습니다(서점)");
			return true;
		}
		//환불하려는 책이 창고에 없고, 소지한 돈이 충분하다면
		else if(refund==null&&money>=refund.price){
			stock.put(refund, 1);
			this.money-=refund.price;
			System.out.println("환불이 정상처리 되었습니다(서점)");
			return true;
		}else {
			System.out.println("환불에 실패하였습니다(서점)");
			return false;
		}
	}
	//5.블랙 리스트 전체 출력(단순 필드값 출력, 입력, 반환값:x)
	public void PrintBlacklist() {
		System.out.println("블랙리스트 목록을 출력합니다.");
		System.out.println("이름, 나이, 등급, 등급 포인트, 거주지");
		for(Consumer black:blacklist) {
			System.out.println(black.name+", "+black.age+", "+black.rating+", "+black.rating_point+", "+black.residence);
		}
	}
	
	//6.블랙 리스트 검색 후 해당 정보 출력(입력, 반환값: x, 내부에서 scanner로 소비자 이름 입력 받는다)
	public void SearchBlack() {
		Scanner sc=new Scanner(System.in);
		Consumer search=null;
		System.out.println("검색할 사용자의 이름을 입력하시오");
		String searchname=sc.nextLine();
		for(Consumer black:blacklist) {
			if(black.name.compareTo(searchname)==0) {
				search=black;
				break;
			}
		}
		if(search!=null) {
			System.out.println(searchname+"씨가 블랙리스트에 존재합니다.");
			System.out.println("이름: "+search.name+", 나이: "+search.age+", 등급: "+search.rating+
					", 등급 포인트: "+search.rating_point+", 거주지: "+search.residence);
		}else {
			System.out.println(searchname+"씨가 블랙리스트에 존재하지 않습니다.");
		}
		
	}
	
	//7.블랙 리스트 등록(입력: 소비자, 반환값: x)
	public void RegisterBlack(Consumer con) {
		boolean isExist=false;
		for(Consumer black:blacklist) {
			if(black.name.compareTo(con.name)==0) {
				isExist=true;
				break;
			}
		}
		if(!isExist) {
			blacklist.add(con);
			System.out.println("블랙리스트에 정상등록 되었습니다.");
		}else {
			System.out.println("이미 등록되어 있는 회원입니다.");
		}
	}
	
	//8.서점 정보 출력(단순 필드값 출력)
	public void PrintBookStore() {
		System.out.println("서점 이름: "+ name+", 소지한 자산: "+money+", 소유자명:"+owner_name);
	}
	
	//9.서점 정보 수정(입력, 반환값:x ,내부에서 scanner로 서점정보를 입력 받는다.)
	public void UpdateBookStore() {
		Scanner sc=new Scanner(System.in);
		System.out.println("현재 정보:\n 지점이름: "+this.name+", 소유자명: "+this.owner_name+", 소지한 금액: "+this.money);
		System.out.println("수정할 정보를 입력하시오:");
		System.out.print("새로운 지점의 이름: ");
		String new_name=sc.nextLine();
		System.out.print("해당 지점의 소유주 이름: ");
		String new_owner_name=sc.nextLine();
		this.name=new_name;
		this.owner_name=new_owner_name;
	}
	
	
	//10.서점 내 책 정보 검색
	public <S extends BookImpl> S SearchBook(BookImpl book) {
		BookImpl bk=null;
		BookImpl search=null;
		if(book instanceof BookImpl) {
			bk=(BookImpl)book;
			
		}else if(book instanceof novel_book){
			bk=(novel_book)book;
			
		}else if(book instanceof Major_book){
			bk=(Major_book)book;
			
		}else if(book instanceof comic_book){
			bk=(comic_book)book;
			
		}else {
			System.out.println("부적절한 입력입니다.");
		}
		for(BookImpl b: stock.keySet()) {
			if(b.title.compareTo(bk.title)==0) {
				search=b;
				break;
			}
		}
		return (S)search;
	}
	
	//11. 신규 책 입력
		public <S extends BookImpl> S inputBook() {
			Scanner sc=new Scanner(System.in);
			System.out.println("책의 종류를 입력하시오(1.소설책, 2.전공책, 3.만화책)");
			S newbook=null;
			int type=sc.nextInt();
			sc.nextLine(); 
			String title="";
			String writer="";
			String publisher="";
			int pri=0;
			String genre="";
			String major="";
			int picture_num=0;
			switch (type) {
			case 1:
				System.out.println("소설책 선택!");
				System.out.println("순서대로 입력하시오(제목 작가 출판사 가격 장르)");
				System.out.print("제목: ");
				title=sc.nextLine();
				System.out.print("작가: ");
				writer=sc.nextLine();
				System.out.print("출판사: ");
				publisher=sc.nextLine();
				System.out.print("가격: ");
				pri=sc.nextInt();
				sc.nextLine(); 
				System.out.print("장르: ");
				genre=sc.nextLine();
				newbook=(S)new novel_book(title, writer, "소설", publisher, pri, genre);
				break;
			case 2:
				System.out.println("전공책 선택!");
				System.out.println("순서대로 입력하시오(제목 작가 출판사 가격 전공)");
				System.out.print("제목: ");
				title=sc.nextLine();
				System.out.print("작가: ");
				writer=sc.nextLine();
				System.out.print("출판사: ");
				publisher=sc.nextLine();
				System.out.print("가격: ");
				pri=sc.nextInt();
				sc.nextLine(); 
				System.out.print("전공 분야: ");
				major=sc.nextLine();
				newbook=(S)new Major_book(title, writer, "전공", publisher, pri, major);
				break;
			case 3:
				System.out.println("만화책 선택!");
				System.out.println("순서대로 입력하시오(제목 작가 출판사 가격 그림수)");
				System.out.print("제목: ");
				title=sc.nextLine();
				System.out.print("작가: ");
				writer=sc.nextLine();
				System.out.print("출판사: ");
				publisher=sc.nextLine();
				System.out.print("가격: ");
				pri=sc.nextInt();
				sc.nextLine(); 
				System.out.print("그림의 수:");
				picture_num=sc.nextInt();
				newbook=(S)new comic_book(title, writer, "만화책", publisher, pri, picture_num);
				break;
			default:
				System.out.println("존재하지 않는 종류 입니다.");
				break;
			}
			
			return newbook;
		}
		
		//12.소비자의 책 구매에 따른 재고 수정(돈)
		public void buyupdatestockmoney(BookImpl buybook) {
			BookImpl search=SearchBook(buybook);
			int stocknum=stock.get(search);
			if(stocknum>0) {
				money+=search.price;
				stock.replace(search, stocknum-1);
			}else if(stocknum==0) {
				System.out.println("재고가 없습니다.");
			}
		}
		//13.소비자의 책 구매에 따른 재고 수정(포인트)
		public void buyupdatestockpoint(BookImpl buybook) {
			BookImpl search=SearchBook(buybook);
			int stocknum=stock.get(search);
			if(stocknum>0) {
				stock.replace(search, stocknum-1);
			}else if(stocknum==0) {
				System.out.println("재고가 없습니다.");
			}
		}
		
		//14.소비자의 블랙리스트 존재여부 검색
		public boolean isBlackList(Consumer con) {
			boolean result=false;
			for(Consumer black:blacklist) {
				if(black.name.compareTo(con.name)==0) {
					result=true;
				}
			}
			return result;
		}
}