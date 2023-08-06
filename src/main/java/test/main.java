package test;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.User;
import util.HibernateUtil;

public class main {
	public static void main(String[] args) {
		// login app
		String luaChon ;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("MENU ---------- ");
			System.out.println("Vui lòng chọn chức năng: ");
			System.out.println(" 1. Đăng ký " + 
								"2. Đăng nhập ");
			luaChon = sc.nextLine();
			sc.nextLine();
			
			if (luaChon.equals("1")) {
				try {
					System.out.println("Mời bạn nhập username: ");
					String userName = sc.nextLine();
					System.out.println("Mời bạn nhập password: ");
					String passWord = sc.nextLine();
					int currentMoney = 5000000;

					HibernateUtil util = new HibernateUtil();
					Session ss =  HibernateUtil.getHibernateSession();
					Transaction trans = ss.beginTransaction();
					
					User user = new User();
					user.setUserName(userName);
					user.setPassWord(passWord);
					user.setCurrentMoney(currentMoney);
					
					ss.saveOrUpdate(user);
					trans.commit();
					
					
				} catch (Exception e) {
					System.out.println("Đăng ký thất bại");
				}
				
			} else if (luaChon.equals("2")){
				try {
					System.out.println("Mời bạn nhập username: ");
					String userName = sc.nextLine();
					System.out.println("Mời bạn nhập password: ");
					String passWord = sc.nextLine();
					
					HibernateUtil util = new HibernateUtil();
					boolean check = util.checkPassword(userName, passWord);
											
					if (check) {
					
					System.out.println("Dang nhap thanh cong");
					Session ss =  HibernateUtil.getHibernateSession();

					Locale lc = new Locale("vi", "VN");
					NumberFormat num = NumberFormat.getCurrencyInstance(lc);
					String luaChon1;
					String luaChon2;
					int currentMoney = util.getCurrentMoney(userName);
					do {
						
						System.out.println("tai khoan cua ban: " + currentMoney);
						if(currentMoney < 50000) {
							System.out.println("tai khoan khong du, nap lan dau di");
							System.out.println("Chon 1 de nap");
							System.out.println("Chon 2 de thoat");
							luaChon2 = sc.nextLine();
							
							if(luaChon2.equals("1")) {
								System.out.println("Nhap so tien can nap");
								int newCurrentMoney = sc.nextInt();
										
								currentMoney = currentMoney + newCurrentMoney;
								if(currentMoney > 50000) {
									System.out.println("nap thanh cong!");
									System.out.println("tai khoan cua ban la: " + currentMoney);
								}else {
									System.out.println("tai khoan khong du, nap tiep di");
								}			
								int result = util.saveCurrentMoney(userName, currentMoney);
								System.out.println(result);
								} else {
									System.out.println("Cut!");
								}
							
							
						}
						

						
						
						
						
						System.out.println("--------Mời bạn lựa chọn----------");
						System.out.println("Chọn (1) để tiếp tục chơi!");
						System.out.println("Chọn (phím bất kỳ) để thoát!");
						luaChon = sc.nextLine();
						if (luaChon.equals("1") && currentMoney > 50000) {
							System.out.println("****Bắt đầu chơi****");
							// Đặt cược
							System.out.println(
									"******Tài khoản của bạn: " + num.format(currentMoney) + " *Bạn muốn đặt cược bao nhiêu*");
							double datCuoc = 0;
							do {
								System.out.println("******Đặt cược: (0 <= số tiền cược <=" + num.format(currentMoney) + "):");
								datCuoc = sc.nextDouble();

							} while (datCuoc <= 0 || datCuoc > currentMoney);

							int luaChonTaiXiu = 0;
							do {
								System.out.println("******Chọn: (1) <-> Tài hoặc (2) <-> Xỉu");
								luaChonTaiXiu = sc.nextInt();
							} while (luaChonTaiXiu != 1 && luaChonTaiXiu != 2);

							// Tung xúc xắc
							Random xucXac1 = new Random();
							Random xucXac2 = new Random();
							Random xucXac3 = new Random();

							int giaTri1 = xucXac1.nextInt(5) + 1;
							int giaTri2 = xucXac1.nextInt(5) + 1;
							int giaTri3 = xucXac1.nextInt(5) + 1;
							int tong = giaTri1 + giaTri2 + giaTri3;
							// Tính toán kết quả
							System.out.println("Kết quả: " + giaTri1 + "-" + giaTri2 + "-" + giaTri3);
							if (tong == 3 || tong == 18) {
								currentMoney -= datCuoc;
								System.out.println("******Tổng là: " + tong + " Nhà cái ăn hết, bạn đã thua");
								System.out.println("******Tài khoản của bạn: " + num.format(currentMoney));
								int result = util.saveCurrentMoney(userName, currentMoney);

							} else if (tong >= 4 && tong <= 10) {
								System.out.println("******Tổng là: " + tong + " Xỉu");
								if (luaChonTaiXiu == 2) {
									System.out.println("****Bạn đã thắng cược!");
									currentMoney += datCuoc;
									System.out.println("*****Tài khoản của bạn là: " + num.format(currentMoney));
									int result = util.saveCurrentMoney(userName, currentMoney);

								} else {
									System.out.println("****Bạn đã thua cược!");

									currentMoney -= datCuoc;
									System.out.println("*****Tài khoản của bạn là: " + num.format(currentMoney));
									int result = util.saveCurrentMoney(userName, currentMoney);

								}

							} else {
								System.out.println("******Tổng là: " + tong + " Tài");
								if (luaChonTaiXiu == 1) {
									System.out.println("****Bạn đã thắng cược!");
									currentMoney += datCuoc;
									System.out.println("*****Tài khoản của bạn là: " + num.format(currentMoney));

								} else {
									System.out.println("****Bạn đã thua cược!");

									currentMoney -= datCuoc;
									System.out.println("*****Tài khoản của bạn là: " + num.format(currentMoney));
									int result = util.saveCurrentMoney(userName, currentMoney);

								}

							}

						}

					} while (luaChon.equals("1"));
						
						System.out.println("save money here!");
						int result = util.saveCurrentMoney(userName, currentMoney);
						
						if (result == 1) {
							System.out.println("luu thanh cong");
						} else {
							System.out.println("luu that bai");

						}
								
								
						
					}else {
						System.out.println("Wrong password or username. Please sign up if you have not account");

					}
					
					
					
					
					
					
				} catch (Exception e) {
					System.out.println("Nhap sai " + e + " moi nhap lai!");
				}
			}
		} while (! luaChon.equals("0"));
		
	}

}
