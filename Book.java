package Thread_project;

public interface Book{
	//Book를 상속해서 다양한 클래스 구현 및 다양한 메소드로 다형성
	//1.책의 전체 정보 출력(일반 책 버전)(단순 필드 값 출력)
	public void Book_inform();
	
	//2.책의 정보 수정(입력: 책, 반환값:같은 하위 클래스, 자식 클래스 별로 다르게 작성)
	public void edit();
	//3.책의 이름, 가격 출력(1월 5일자)
	public String getName(); 
    public int getPrice();
}