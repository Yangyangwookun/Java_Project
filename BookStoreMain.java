package Thread_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookStoreMain {
	public static ArrayList<Consumer> conlist;
	public static BookStore bookStore;
	public static void helloBookStore() {
		System.out.println("=============================================");
		System.out.println("       안녕하세요! JAVA 서점에 온것을 환영합니다.     ");
		System.out.println("=============================================");
	}
	public static void SelectPrint1() {
		System.out.println("=============================================");
		System.out.println("  사업자로 접속인지, 아니면 소비자로 접속인지 선택하세요  ");
		System.out.println("               1.소비자 2.서점 3.종료            ");
		System.out.println("=============================================");
	}
	public static void Update_Consumer_List(Consumer update) {
		for(int i=0;i<conlist.size();i++) {
			Consumer c=conlist.get(i);
			if(c.name.compareTo(update.name)==0) {
				conlist.remove(i);
				conlist.add(i, update);
			}
		}
	}
	public static Consumer SelectPrint_consumer(String name) {
		System.out.println();
		boolean IsExist=false;
		Consumer user=null;
		for(Consumer c:conlist) {
			if(c.name.compareTo(name)==0) {
				IsExist=true;
				user=c;
				System.out.println("안녕하세요! "+name+" 씨");
				break;
			}
		}
		if(!IsExist){
			System.out.println("존재하지 않는 회원입니다.");
			user=null;
		}
		return user;
		
	}
	public static void SelectPrint_consumer2() {
		System.out.println("아래의 옵션 중에서 선택해주세요");
		System.out.println("=============================================================================");
		System.out.println("1.책 구입(포인트 적립 포함) 2.책 환불 3.포인트 이용한 책 구입 4.회원 정보 출력 5.회원 정보 수정");
		System.out.println("                                  6.뒤로가기                                   ");
		System.out.println("=============================================================================");
	}
	public static void SelectPrint_store() {
		System.out.println("서점 관리자로 접속하였습니다. 아래의 옵션 중에서 선택해주세요.");
		System.out.println("=========================================================");
		System.out.println("1.재고 등록 2.재고정보 수정 3.소유한 책정보 출력 4.블랙리스트 전체 출력");
		System.out.println(" 5.블랙리스트 검색 후 해당정보 출력 6.블랙리스트 등록 7.서점정보 출력  ");
		System.out.println("        8.서점 정보 수정 9.서점 내 책 정보 검색 10.뒤로가기        ");
		System.out.println("=========================================================");
	}
	
	
	public static void main(String[] args) {
		BookStoreMain main=new BookStoreMain();
		conlist=new ArrayList<Consumer>();
		String[] userlist= {"윤석효", "김서빈", "진수진"};
		
		//원활한 진행을 위해서 이미 회원들이 3명 가량 가입해있다고 가정(각각 돈이 백만원 있다고 가정)
		for(int i=0;i<3;i++) {
			String name=userlist[i];
			int age=i;
			int point=0;
			int money=1000000;
			String rating="bronse";
			String residence="Seoul";
			int rating_point=0;
			conlist.add(new Consumer(new ArrayList<BookImpl>(), 
					name, age, point, money, rating, residence, rating_point
					,new ArrayList<Double>(),new ArrayList<Boolean>()));
		}
		
		//서점 정보 초기화
		Scanner sc=new Scanner(System.in);
		bookStore=new BookStore();
		bookStore.stock=new HashMap<BookImpl, Integer>();
		bookStore.name="JAVA ";
		bookStore.money=1000000000;
		bookStore.owner_name="홍길동";
		bookStore.blacklist=new ArrayList<Consumer>();
		
		System.out.println("초기 재고 값을 입력하시오(테스트용)");
		int i=0;
		//임의의 책 추가
		
		System.out.println("먼저 추가할 책의 수를 입력하시오: ");
		int booknum=sc.nextInt();
		
		while(i<booknum) {
			
			BookImpl book=bookStore.inputBook();
			if(book!=null) {
				System.out.print("책의 수를 입력하시오: ");
				int num=sc.nextInt();
				bookStore.stock.put(book, num);
				i++;
			}else {
				System.out.println("잘못된 입력입니다.");
			}
			
		}
		
		while(true) {
			//환영 인사, 서점, 소비자, 종료 여부 선택 문구
			helloBookStore();
			SelectPrint1();
			
			int select1= -1;
            while (select1 == -1) {
                try {
                    select1 = sc.nextInt();
                    sc.nextLine(); // 입력 버퍼 처리
                } catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
                    sc.nextLine(); // 잘못된 입력 후 버퍼 비우기
                }
            }

			//3번 선택 시 프로그램 종료
			if(select1==3) {
				System.out.println("서점 프로그램 종료!");
				break;
			}
			
			//1번 선택 시 소비자로 프로그램 접속
			else if(select1==1) {
				//여기에 이름을 입력받고 consumer 클래스를 출력하는 함수
				System.out.print("접속할 소비자의 이름을 입력하시오");
				String con=sc.nextLine();
				Consumer user=SelectPrint_consumer(con);
				boolean isBlackList=bookStore.isBlackList(user);
				if(user!=null&&!isBlackList) {
					//소비자가 6번 선택을 하기 전까지 while 반복
					while(true) {
						SelectPrint_consumer2();

						/*
						 * 조작할시 store 내 책 제고, 잔금 과 해당 소비자의 책과 돈, 포인트, 등급 등의 변경이 있어야한다.
						 * 1.책 구입(포인트 적립 포함), 2.책 환불, 3. 포인트 이용한 책 구입, 4.회원정보 출력, 5.회원정보 수정, 
						 * 6.뒤로가기
						 */
						int select2 = -1;
	                       while (select2 == -1) {
	                           try {
	                               select2 = sc.nextInt();
	                               sc.nextLine(); // 입력 버퍼 처리
	                           } catch (InputMismatchException e) {
	                               System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
	                               sc.nextLine(); // 잘못된 입력 후 버퍼 비우기
	                           }
	                    }

						switch(select2) {
							case 1:

								/*
								책을 고른다. 책의 가격과 소지한 돈을 비교한다.
								만약 책 구입을 돈으로만 한다면 재고 수정, 
								책의 구입과 포인트 적립을 멀티 쓰레드로 동시에 실행한다.
							    */
								
								System.out.println("책 구입을 선택하셨습니다. 현재 서점이 보유하고 있는 책 목록 입니다.");
								bookStore.PrintStock();
								System.out.println("구입하실 책의 이름을 입력하시오");
								String BuyBookTitle=sc.nextLine();
								BookImpl search=new BookImpl(BuyBookTitle, null, null, null, 0);
								BookImpl result=bookStore.SearchBook(search);
								if (result == null) {
								    System.out.println("책을 찾을 수 없습니다.");
								    continue;
								}
								int resultprice=result.price;
								if(result!=null&&resultprice<=user.money) {
									/*
									buyBookWithMoney, updatebuystock, earnPoints 세개를 멀티쓰레드로 동시 실행
									셋중 하나만 수행하면 안되니까 동기화로 실행
									*/
									synchronized (user) {
										Thread buythread=new Thread(new Runnable() {
											@Override
											public void run() {
												user.buyBookWithMoney(result);
											}
										});
										Thread updatebuystock=new Thread(new Runnable() {
											@Override
											public void run() {
												bookStore.buyupdatestockmoney(result);
											}
										});
										Thread pointthread=new Thread(new Runnable() {
											@Override
											public void run() {
												int Point=(int)(resultprice*0.1);
												user.earnPoints(Point);
											}
										});
									
										buythread.start();
										updatebuystock.start();
										pointthread.start();
										try {
										       buythread.join();
										       updatebuystock.join();
										       pointthread.join();
										}catch (Exception e) {
										       e.printStackTrace();
										}
									}
									Update_Consumer_List(user);
								}else {
									System.out.println("돈을 이용한 구입에 실패했습니다.");
								}
								break;
								
							case 2:
								
								System.out.println("현재 구입하신 책 목록 입니다. 포인트 이용한 책구입은 환불이 불가능 합니다.");
								//Consumer의 보유 책 리스트 출력 메소드 하나
								user.booklist();
								System.out.print("환불하실 책의 제목을 입력하시오");
								String refundtitle=sc.nextLine();
								BookImpl refund=bookStore.SearchBook(new BookImpl(refundtitle, null, null, null, 0));
								BookImpl refundconsumer=null;
								boolean isExist=false;
								boolean isPoint=false;
								double olddiscount=0;
								int dis=0;
								for(dis=0;dis<user.discountlist.size();dis++) {
									BookImpl consumerbook=user.booklist.get(dis);
									if(consumerbook.title.compareTo(refundtitle)==0) {
										olddiscount=user.discountlist.get(dis);
										refundconsumer=consumerbook;
										isExist=true;
										isPoint=user.isBuyPoint.get(dis);
										break;
									}
								}
								/*
								 * BookImpl refundbook= Consumer의 소지한 책 리스트에서 책제목을 입력받고 해당 책을 찾는 메소드
								 * 소비자가 책을 먼저 가지고 있는지 확인
								 * 책 환불 처리 함수(소비자), 책 환불 처리 함수(서점)을 동시에 실행
								 * 만약 둘다 만족하면 환불 성공
								 */
								int userpurchase=(int)(refund.price*(1-olddiscount));
								if(isExist&&bookStore.RefundBook(refund)
										&&!user.refundbook(refund)&&user.money>=userpurchase) {
									System.out.println("책 환불이 정상처리 되었습니다.");
									
									int discountprice=userpurchase;
									
									user.point-=(int)(refund.price*0.1);
									if(user.point<0) {
										user.point=0;
									}
									
									user.money+=discountprice;
									user.rating_point-=discountprice;
									
									user.updateRating();
									user.booklist.remove(refundconsumer);
									user.discountlist.remove(dis);
									user.isBuyPoint.remove(dis);
									Update_Consumer_List(user);
								}else {
									System.out.println("책 환불에 실패하였습니다.");
								}
								
								break;
							case 3:
								System.out.print("포인트를 이용한 책 구입을 선택하셨습니다. 현재 서점이 보유하고 있는 책 목록 입니다.");
								bookStore.PrintStock();
								System.out.println("구입하실 책의 이름을 입력하시오");
								String PointBuyBookTitle=sc.nextLine();
								BookImpl Psearch=new BookImpl(PointBuyBookTitle, null, null, null, 0);
								BookImpl Presult=bookStore.SearchBook(Psearch);
								if(Presult!=null&&Presult.price<=user.point) {
									user.buyBookWithPoints(Presult);
									bookStore.buyupdatestockpoint(Presult);
									Update_Consumer_List(user);
								}else {
									System.out.println("포인트를 이용한 구입에 실패했습니다.");
								}
								break;
							case 4:
								System.out.println(user.name+"씨의 회원정보 및 구매하신 책 목록입니다.");
								user.printConsumerInfo();
								//회원 정보 출력 및 소유한 책 정보 출력
								break;
							case 5:
								System.out.println(user.name+"씨의 회원정보 입니다.");
								user.printConsumerInfo();
								System.out.println("새로운 이름을 입력하시오");
								String newname=sc.nextLine();
								System.out.println("새로운 나이를 입력하시오");
								int newage=sc.nextInt();
								sc.nextLine();
								System.out.println("새로운 거주지를 입력하시오");
								String newresidence=sc.nextLine();
								user.updateConsumerInfo(newname, newage, newresidence);
								Update_Consumer_List(user);
								break;
							case 6:
								System.out.println(user.name+" 회원님의 접속을 종료합니다.");
								break;
							default:
								System.out.println("부적절한 선택입니다.");
								break;
						}
						
						if(select2==6) {
		                     break;
		                  }
		               }
		               
		            }
		            else {
		               System.out.println("존재하지 않는 회원 이므로 다시 처음으로 이동합니다.");
		               continue;
		            }
		            
		            
		         }

			//2번 선택 시 서점 관리자로 프로그램 접속
			else if(select1==2) {
				while(true) {
					SelectPrint_store();

					//조작할시 store 내 책 제고, 잔금, 정보 등의 변경이 있어야한다.
					/*
				  	서점 관리자
			      	1.재고 등록 2.재고정보 수정 3.소유한 책정보 출력
				  	4.블랙리스트 전체 출력 5.블랙리스트 검색 후 해당정보 출력
				  	6.블랙리스트 등록 7.서점정보 출력
 				  	8.서점 정보 수정 9.서점 내 책 정보 검색 10.뒤로가기
					*/
					int select2=sc.nextInt();
					sc.nextLine();
					switch(select2) {
						case 1:
							System.out.println("현재 보유하신 책의 목록입니다.");
							bookStore.PrintStock();
							System.out.println("신규 책을 등록합니다.");
							BookImpl newbook=bookStore.inputBook();
							bookStore.RegisterBook(newbook);
							break;
						case 2:
							System.out.println("현재 보유하신 책의 목록입니다.");
							bookStore.PrintStock();
							System.out.println("재고를 수정합니다.");
							System.out.println("책의 이름을 입력하시오");
							String booktitle=sc.nextLine();
							BookImpl search=new BookImpl(booktitle, null, null, null, 0);
							BookImpl updatebook=bookStore.SearchBook(search);
							bookStore.UpdateStock(updatebook);
							break;
						case 3:
							System.out.println("현재 보유하신 책의 목록입니다.");
							bookStore.PrintStock();
						break;
						case 4:
							bookStore.PrintBlacklist();
							break;
						case 5:
							bookStore.SearchBlack();
							break;
						case 6:
							System.out.println("블랙리스트 신규 등록 입니다.");
							System.out.println("블랙리스트에 등록할 유저 이름을 입력하시오:");
							String blackusername=sc.nextLine();
							Consumer black=null;
							for(Consumer c:conlist) {
								if(c.name.compareTo(blackusername)==0) {
									black=c;
									break;
								}
							}
							if(black==null) {
								System.out.println("블랙 리스트에 추가할 소비자를 찾을 수 없습니다.");
							}else {
								bookStore.RegisterBlack(black);
							}
							break;
						case 7:
							bookStore.PrintBookStore();
							break;
						case 8:
							bookStore.UpdateBookStore();
							break;
						case 9:
							System.out.println("서점에서 책을 검색합니다. 제목을 입력하시오");
							String searchtitle=sc.nextLine();
							BookImpl getbook=bookStore.SearchBook(new BookImpl(searchtitle, null, null, null, 0));
							if(getbook==null) {
								System.out.println("검색하신 책이 없습니다.");
							}else {
								System.out.println("검색 결과 입니다");
								System.out.println("제목: "+getbook.title+", 작가: "+getbook.writer
										+", 종류: "+getbook.Type+", 출판사: "+getbook.publisher+", 가격: "+getbook.price);
							}
							break;
						case 10:
							System.out.println("서점 관리자 접속을 종료합니다.");
							break;
						default:
							break;
					}
				
					if(select2==10) {
						break;
					}
				}
				
			}else {
				System.out.println("잘못된 선택입니다.");
			}
			
			
		}
	}
}