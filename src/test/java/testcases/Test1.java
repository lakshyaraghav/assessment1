package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.*;


public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://www.amazon.in/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("lg soundbar" + Keys.ENTER);
        Thread.sleep(Duration.ofSeconds(3));
        List<WebElement> productNames = driver.findElements(By.xpath("//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']/span"));
        List<WebElement> productPrices = driver.findElements(By.xpath("//div[@data-cy='price-recipe']"));


        List<Productdetails> detail = new ArrayList<>();

        for (int i = 0; i < productPrices.size(); i++) {
            WebElement divElement = productPrices.get(i);

            List<WebElement> liElements = divElement.findElements(By.xpath(".//span[@class='a-price-whole']"));

            if (liElements.isEmpty()) {
                String productName = productNames.get(i).getText();
//            System.out.println(0+ " " + productName);
                detail.add(new Productdetails(0, productName));
            } else {
                String productName = productNames.get(i).getText();
                double productPrice = Double.parseDouble(liElements.get(0).getText().replace(",", ""));
//                System.out.println(" productPrice+ " " + productName);
                detail.add(new Productdetails(productPrice, productName));
            }
        }

        System.out.println("\nPrinting all product details:");
        Collections.sort(detail, new Comparator<Productdetails>() {
            @Override
            public int compare(Productdetails p1, Productdetails p2) {
                return Double.compare(p1.getProductPrice(), p2.getProductPrice());
            }
        });

        for (int i = 0; i < detail.size(); i++) {
            Productdetails product = detail.get(i);
            System.out.println(product.getProductPrice() + "  " + product.getProductName());
        }

//        driver.quit();

    }
}


//here i create a Productdetails class and create a construct within a class for passing the price and name
class Productdetails {
    private String productName;
    private double productPrice;

    Productdetails(double productPrice, String productName) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    String getProductName() {
        return this.productName;
    }

    double getProductPrice() {
        return this.productPrice;
    }
}


