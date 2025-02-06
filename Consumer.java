package Thread_project;

import java.util.ArrayList;

public class Consumer {
	public String name;
	public int age;
	public int point;
	public int money;
	public String rating; //(vvip: 500000, gold: 300000, silver: 100000, bronse: 50000)
	public int rating_point;
	public String residence;
	public ArrayList<BookImpl> booklist;
	public ArrayList<Double> discountlist;//구매시에 적용되었던 할인율 저장
	public ArrayList<Boolean> isBuyPoint;//포인트 사용여부 저장
	
	public Consumer() {
		
	}
	public Consumer(ArrayList<BookImpl> booklist,String name, int age, int point, int money
			, String rating,String residence, int rating_point,
			ArrayList<Double> discountlist,ArrayList<Boolean> isBuyPoint) {
        this.booklist = booklist;
        this.name = name;
        this.age = age;
        this.point = point;
        this.money = money;
        this.rating = rating;
        this.residence = residence;
        this.rating_point=rating_point;
        this.discountlist=discountlist;
        this.isBuyPoint=isBuyPoint;
     }
	 //신규 생성자 추가(1월 5일자)
	 public Consumer(String name,int age,int money,String residence) {
        this.name=name;
        this.age=age;
        this.money=money;
        this.point=0;
        this.rating_point=0;
        this.rating="bronze";
        this.residence=residence;
        this.booklist=new ArrayList<>();
    }
	//1,2를 쓰레드로 동시 실행
	
	//1.책 구입(할인 적용)(입력: 책, 반환값x)(1월 5일자)(포인트 적립 부분은 2번에서 따로 처리할게요(멀티쓰레드 활용))
	 public boolean buyBookWithMoney(BookImpl book) {
	        int price=book.getPrice();
	        double discount= getDiscountRate();
	        int discountedPrice=(int)(price* (1 -discount));

	        if (money>=discountedPrice) {
	            money-=discountedPrice;
	            discountlist.add(discount);
	            isBuyPoint.add(false);
	            //point+=discountedPrice*0.1; 
	            rating_point+= discountedPrice;
	            booklist.add(book);

	            updateRating(); 
	            System.out.println(name+"님이 "+book.getName()+ "을" +discountedPrice +"원에 구매했습니다. (할인율: " + (discount * 100) + "%)");
	            return true;
	        } else {
	            System.out.println(name+"님의 잔액이 부족하여 구매할 수 없습니다.");
	            return false;
	        }
	    }

	
	
	//2.포인트 적립(입력: 책의 가격, 반환값: 등급)
	  public void earnPoints(int amount) {
	        point += amount;
	        System.out.println(amount + " 포인트가 적립되었습니다. 현재 포인트: " + point);
	    }

	//3.책 환불(입력: 책, 반환값:true/false)(만약 환불하면 포인트를 깍고, 등급도 원위치 시키는 식으로 동작)(책 존재하고 환불 정상 처리시 false)
	public boolean refundbook(BookImpl refund) {
		boolean result=true;
		boolean isPoint=true;
		BookImpl Search=null;
		//책의 존재 확인
		int i=0;
		for(i=0;i<booklist.size();i++) {
			BookImpl bk=booklist.get(i);
			if(bk.title.compareTo(refund.getName())==0) {
				result=false;
				Search=bk;
				break;
			}
		}
		
		if(isBuyPoint.get(i)) {
			result=true;
		}
		return result;
	}
	  
	  
	//4.포인트 이용(입력: 책, 반환값 x)(1월 5일자)
	 public boolean buyBookWithPoints(BookImpl book) {
	        int price = book.getPrice();

	        if (point>=price) {
	            point-=price;
	            booklist.add(book);
	            isBuyPoint.add(true);
	            System.out.println(name+ "님이 "+book.getName() +"을포인트로 구매했습니다.남은 포인트:"+ point +"점");
	            return true;
	        } else {
	            System.out.println(name + "님의 포인트가 부족하여 구매할 수 없습니다.");
	            return false;
	        }
	    }

	
	
	//5.회원 정보 출력(말 그대로 회원정보 출력(입력,반환값x))(1월 5일자)
	 public void printConsumerInfo() {
	        System.out.println("이름: "+name);
	        System.out.println("나이: "+age);
	        System.out.println("거주지: "+residence);
	        System.out.println("잔액: "+ money+"원");
	        System.out.println("포인트: " +point + "점");
	        System.out.println("등급: "+ rating +" (등급 포인트: " + rating_point +")");
	        System.out.println("구매한 책 목록:");
	        for (BookImpl book : booklist) {
	            System.out.println(" - " +book.getName()+ " (" +book.getPrice() +"원)");
	        }
	    }

	//6.회원 정보 수정(말 그대로 수정, 입력:회원,(반환값x))(1월 5일자)
	 public void updateConsumerInfo(String newName, int newAge, String newResidence) {
	        this.name = newName;
	        this.age = newAge;
	        this.residence = newResidence;
	        System.out.println("회원 정보가 수정되었습니다.");
	        System.out.println("새로운 이름: " + name + ", 나이: " + age + ", 거주지: " + residence);
	    }
	 
	 
	//7.고객의 등급 업데이트(1월 5일자)
	public void updateRating() {
	        if (rating_point>=500000) {
	            rating ="vvip";
	        } else if (rating_point >= 300000) {
	            rating="gold";
	        } else if (rating_point >= 100000) {
	            rating="silver";
	        } else if (rating_point >= 50000) {
	            rating="bronze";
	        } else {
	            rating ="normal";
	        }
	}
	
	//8.고객의 등급에 따른 할인율(1월 5일자)
	public double getDiscountRate() {
        switch (rating) {
            case "vvip":
                return 0.2; // 20% 할인
            case "gold":
                return 0.15; // 15% 할인
            case "silver":
                return 0.1; // 10% 할인
            case "bronze":
                return 0.05; // 5% 할인
            default:
                return 0.0; // 할인 없음
        }
    }
	//9. 고객이 소유한 책 리스트 콘솔 출력(단순 booklist 요소들 콘솔에 출력, 반환값 x)
	 public void booklist() {
	      
	      if(booklist.isEmpty()) {
	         System.out.println("현재 고객님이 갖고계신 책이 없습니다.");
	         
	      } else {
	         for(Book book : booklist)// 클래스. 변수명적고 :적고 자료구조적음
	           book.Book_inform();
	           System.out.println();
	      }   
	  }

}