package com.porche;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PorscheWebsiteAutomation {


		public static Integer isDigit(String letter) {

			String num = "";

			for (int i = 0; i < letter.length(); i++) {
				if (letter.charAt(i) == '.') {
					break;
				}
				if (Character.isDigit(letter.charAt(i))) {

					num += letter.charAt(i);
				}
			}

			return Integer.parseInt(num);
		}

		public static void main(String[] args) throws InterruptedException {

			WebDriverManager.chromedriver().setup();
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			// 2.Go to url “https://www.porsche.com/usa/modelstart/”
			String url = "https://www.porsche.com/usa/modelstart/";
			driver.get(url);
			// getting parent window session id.
			String parent = driver.getWindowHandle(); // this one takes first window session
			System.out.println("Parent window : " + parent);

			// 3.Click model 718.// 4.Remember the price of 718Cayman.
			driver.findElement(By.xpath("//a[@href='/usa/modelstart/all/?modelrange=718']")).click();
			// 4.Remember the price of 718Cayman. // 5.Click on Build & Price under
			// 718Cayman
			int parentBasePrice = isDigit(
					driver.findElement(By.xpath("//*[@id=\"m982120\"]/div[1]/div[2]/div[2]")).getText());
			System.out.println("Base Price : $" + parentBasePrice);
			// 5.Click on Build & Price under 718Cayman
			driver.findElement(By.xpath("(//a[@class='m-01-link m-14-build'])[1]")).click();
			// we are assigning all the open windows by method getWindowhandles() to the
			// set.
			Set<String> allWindows = driver.getWindowHandles();// this one collects all open windows
			// it shows the size of how many window is opening
			int countWindow = allWindows.size();
			System.out.println("Total Window " + countWindow);
			// we are iterating each window session ids
			for (String child : allWindows) {
				// if parent window is not equal to child window
				if (!parent.equalsIgnoreCase(child)) {
					// then switch to child window
					driver.switchTo().window(child);
					// print the child window
					System.out.println("Child window: " + child);

				}
			}
			// 6. Verify that Base price displayed on the page is same as the price from
			// step 4
			int childPagePrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[5]")).getText());
			findPrices(parentBasePrice, childPagePrice);

			// 7. Verify that Price for Equipment is 0
			int equipmentPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			findPrices(equipmentPrice, 0);

			// 8. Verify that total price is the sum of base price + Delivery, Processing
			// and Handling Fee
			int totalPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
			int deliveryHandlingFee = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
			findPrices(totalPrice, childPagePrice + deliveryHandlingFee);

			// 9.Select color “Miami Blue”
			driver.findElement(By.id("s_exterieur_x_FJ5")).click();

			// 10. Verify that Price for Equipment is Equal to Miami Blue price
			equipmentPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			int miamiBluePrice = isDigit(driver.findElement(By.id("s_exterieur_x_FJ5")).getAttribute("data-price"));
			findPrices(equipmentPrice, miamiBluePrice);

			// 11. Verify that total price is the sum of base price + Price for Equipment +
			// Delivery,Processing and Handling Fee
			totalPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
			deliveryHandlingFee = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
			equipmentPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			findPrices(totalPrice, childPagePrice + deliveryHandlingFee + equipmentPrice);

			// 12. Select 20" Carrera Sport Wheels
			// 1).open the Overview expander first!
			driver.findElement(By.xpath("(//div[@class='flyout-label-value'])[1]")).click(); //
			Thread.sleep(1000);
			// 2).click on wheels from expender WINDOW
			driver.findElement(By.xpath("//div[@id='submenu_exterieur_x_AA_submenu_x_IRA']")).click();
			Thread.sleep(1000);
			// 3).click on 20" Carrera Sport Wheels
			driver.findElement(By.xpath("//li[@id='s_exterieur_x_MXRD']")).click();

			// 13. Verify that Price for Equipment is the sum of Miami Blue price + 20"
			// Carrera Sport Wheels
			Thread.sleep(1000);
			equipmentPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			int price20CarreraWheels = isDigit(driver.findElement(By.id("s_exterieur_x_MXRD")).getAttribute("data-price"));
			findPrices(equipmentPrice, (miamiBluePrice + price20CarreraWheels));

			// 14. Verify that total price is the sum of base price + Price for Equipment +
			// Delivery,Processing and Handling Fee
			totalPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
			deliveryHandlingFee = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
			equipmentPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			findPrices(totalPrice, childPagePrice + deliveryHandlingFee + equipmentPrice);

			// 15. Select seats ‘Power Sport Seats (14-way) with Memory Package’
			// open the Overview expander first
			driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
			// select interior Colors and Seats
			driver.findElement(By.id("submenu_interieur_x_AI_submenu_x_submenu_parent")).click();
			Thread.sleep(1000);
			// click on seats
			driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Seats']")).click();
			// select ‘Power Sport Seats (14-way) with Memory Package’
			Thread.sleep(1000);
			driver.findElement(By.xpath("(//div[@class='seat'])[2]")).click();

			// 16. Verify that Price for Equipment is the sum of Miami Blue price + 20"
			// Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
			Thread.sleep(1000);
			equipmentPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			int pricePowerSportSeats = isDigit(
			driver.findElement(By.xpath("(//div[@class='seat'])[2]//div[@class='pBox']/div")).getText());
			Thread.sleep(1000);
			findPrices(equipmentPrice, miamiBluePrice + price20CarreraWheels + pricePowerSportSeats);

			// 17. Verify that total price is the sum of base price + Price for Equipment +
			// Delivery, Processing and Handling Fee
			totalPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
			deliveryHandlingFee = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
			equipmentPrice = isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			findPrices(totalPrice, childPagePrice + deliveryHandlingFee + equipmentPrice);

			// 18. Click on Interior Carbon Fiber
			// open the Overview expander again
			driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
			Thread.sleep(1000);
			// click on options
			driver.findElement(By.id("submenu_individualization_x_individual_submenu_x_submenu_parent")).click();
			Thread.sleep(1000);
			// choose interior  carbon fiber and sending page down
			driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Interior Carbon Fiber']")).sendKeys(Keys.PAGE_DOWN);
			Thread.sleep(1000);
//			driver.findElement(By.id("submenu_individualization_x_individual_submenu_x_IIC")).click();
//			Thread.sleep(1000);
			
			// 19. Select Interior Trim in Carbon Fiber i.c.w. Standard Interior
			Thread.sleep(2000);
		    driver.findElement(By.xpath("//div[@id='submenu_individualization_x_individual_submenu_x_IIC']")).click();
			
		     //20. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. Standard Interior
			Thread.sleep(2000);
			int priceCarbonFiber= isDigit(driver.findElement(By.xpath("//div[@id='vs_table_IIC_x_PEKH']//div[@class='box']//div[@class='pBox']/div")).getText());
			equipmentPrice= isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			findPrices(equipmentPrice,miamiBluePrice+price20CarreraWheels+pricePowerSportSeats+priceCarbonFiber);
			
	        //21. Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
			totalPrice=  isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
			deliveryHandlingFee= isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
			equipmentPrice= isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			findPrices(totalPrice,childPagePrice+deliveryHandlingFee+equipmentPrice);
			
			//22. Click on Performance
			//open the Overview expander again
			driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
			Thread.sleep(2000);
			//click on performance
			driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Performance']")).click();
			Thread.sleep(2000);
			
			//23. Select 7-speed Porsche Doppelkupplung (PDK)
			WebElement speed7PorscheDoppelkupplung=driver.findElement(By.xpath("//div[@data-link-id='M250']"));
			speed7PorscheDoppelkupplung.click();
			Thread.sleep(2000);
			int price7SpeedPorscheDoppelkupplung = isDigit(driver.findElement(By.xpath("//div[@id='vs_table_IMG_x_M250']//div[@class='pBox']/div")).getText());
			
			//24. Select Porsche Ceramic Composite Brakes (PCCB)
			//open the Overview expander again
			driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
			Thread.sleep(2000);
			//click on performance
			driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Performance']")).click();		
			Thread.sleep(2000);
			//getting performance again to send page down 
			driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Performance']")).sendKeys(Keys.PAGE_DOWN);
			Thread.sleep(2000);
			driver.findElement(By.id("vs_table_IMG_x_M450_x_c94_M450_x_shorttext")).click();
			//getting the price of PCCB
			int priceOfPCCB = isDigit(driver.findElement(By.xpath("//div[@id='vs_table_IMG_x_M450']//div[@class='pBox']/div")).getText());
			
			//25. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in
	    	//Carbon Fiber i.c.w. Standard Interior + 7-speed Porsche Doppelkupplung (PDK) +
			//Porsche Ceramic Composite Brakes (PCCB)
		    Thread.sleep(2000);
		    equipmentPrice= isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		    findPrices(equipmentPrice,miamiBluePrice+price20CarreraWheels+pricePowerSportSeats+priceCarbonFiber
									+price7SpeedPorscheDoppelkupplung+priceOfPCCB);
		    
		    //26. Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
			totalPrice=  isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
			deliveryHandlingFee= isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
			equipmentPrice= isDigit(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
			findPrices(totalPrice,childPagePrice+deliveryHandlingFee+equipmentPrice);
		    
		    Thread.sleep(10000);
	         driver.quit();
	        System.out.println("Test completed. "+LocalDateTime.now());
	         
	         
		}

		private static void findPrices(int parentBasePrice, int childPagePrice) {
			// finding if both price are same
			if (parentBasePrice == childPagePrice) {
				System.out.println("PASSED-->. \t$" + parentBasePrice + "\t= $" + childPagePrice);
			}



		}

	}


	


