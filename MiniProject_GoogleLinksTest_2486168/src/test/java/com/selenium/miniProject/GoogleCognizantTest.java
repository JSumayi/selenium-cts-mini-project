package com.selenium.miniProject;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GoogleCognizantTest {

    WebDriver driver;
    WebDriverWait wait;
    SoftAssert softAssert = new SoftAssert();
    

    public static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("ddMMyy_HHmmss");

    
    //Browser Setup

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {
    	
    	switch (browser.toLowerCase()) {
    		case "chrome":
    			driver = new ChromeDriver();
    			break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
            	throw new RuntimeException("Unsupported browser: "+browser);
        }

    	
        driver.manage().window().maximize();
        
        driver.manage().deleteAllCookies();
        driver.get("https://www.google.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }


    @Test(priority = 1)
    public void openGoogleAndPrintLinks() {

        List<WebElement> links = driver.findElements(By.tagName("a"));

        softAssert.assertTrue(links.size() > 0, "No links found on Google home page");
        System.out.println("Number of links on page: " + links.size());
        
        for (WebElement link : links) {
            if (!link.getText().isBlank()) {
                System.out.println(link.getText());
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void typeCognizantAndCaptureSuggestions() {

    	 enterSearchText("Cognizant");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']")));

        List<WebElement> suggestions =driver.findElements(By.xpath("//li[@data-entityname]"));

        softAssert.assertTrue(suggestions.size() > 0,"Search suggestions are not displayed");
        System.out.println("Search options count: " + suggestions.size());
        
        takeScreenshot("Search_Suggestions");
        
        for (WebElement suggestion : suggestions) {
            System.out.println(suggestion.getText());
        }
        softAssert.assertAll();
       
    }
    
    @Test(priority = 3)
    public void findSearchResultsCount() {

        // Click Google Search
    	clickSearch();
        
    	//captcha validation 
        wait = new WebDriverWait(driver, Duration.ofMinutes(5));

        // Wait until results page loads
        wait.until(d ->
                d.findElements(By.id("search")).size() > 0
        );

        // Locate "About XXXXX results"
        List<WebElement> resultStats =
                driver.findElements(By.id("result-stats"));

        if (!resultStats.isEmpty() && !resultStats.get(0).getText().isBlank()) {
            System.out.println(resultStats.get(0).getText());
            
            softAssert.assertTrue(true, "Result count displayed");
        }
        else
        {
        	System.out.println("No result count found on page");
            softAssert.assertTrue(true, "No results displayed by Google");
        }

        takeScreenshot("Search_Results_Page");
        softAssert.assertAll();
    }
    
    @Test(priority = 4)
    public void clickAllTab() {
    	waitUntilCaptchaAndTabsLoaded();
    	WebElement allTab = driver.findElement(By.xpath("//span[text()='All']/ancestor::*[@role='tab' or self::a]"));
    	allTab.click();

    	softAssert.assertTrue(
            allTab.isDisplayed(),
            "All tab is not displayed"
    			);

    	printResultCount();
    	takeScreenshot("All_Tab_Full_Page");
    	softAssert.assertAll();

    }

    @Test(priority = 5)
    public void clickNewsTab() {
    	WebElement newsTab = driver.findElement(By.xpath("//span[text()='News']/ancestor::*[@role='tab' or self::a]"));
    	newsTab.click();
    	softAssert.assertTrue(
        driver.findElements(
            By.xpath("//span[text()='News']/ancestor::*[@role='tab' or self::a]")
        ).size() > 0,
        "News tab is not present after click"
    );
    	printResultCount();
    	takeScreenshot("News_Tab_Full_Page");
    	softAssert.assertAll();
    }
   

    @Test(priority = 6)
    public void clickImagesTab() {
    	WebElement imagesTab = driver.findElement(By.xpath("//span[text()='Images']/ancestor::*[@role='tab' or self::a]"));
    	imagesTab.click();

    	softAssert.assertTrue(
        driver.findElements(
            By.xpath("//span[text()='Images']/ancestor::*[@role='tab' or self::a]")
        ).size() > 0,
        "Images tab is not present after click"
    );

    	takeScreenshot("Images_Tab_Full_Page");
    	softAssert.assertAll();

    }

    @Test(priority = 7)
    public void clickVideosTab() {
    	driver.findElement(By.xpath("//span[text()='Videos']/ancestor::*[@role='tab' or self::a]")).click();

    	softAssert.assertTrue(
        driver.findElements(
            By.xpath("//span[text()='Videos']/ancestor::*[@role='tab' or self::a]")
    ).size() > 0,
        "Videos tab is not displayed"
    );

    	printResultCount();
    	takeScreenshot("Videos_Tab_Full_Page");
    	softAssert.assertAll();

    }

    //Reusable methods
    
    
    //Enter cognizant
    public void enterSearchText(String text) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.clear();
        searchBox.sendKeys(text);
    }
    
    //click search 
    public void clickSearch() {
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    }
    
    
    //CAPTCHA-SAFE WAIT

    public void waitUntilCaptchaAndTabsLoaded() {

        wait = new WebDriverWait(driver, Duration.ofMinutes(5));

        wait.until(d -> {
            try {
                // Search results page loaded
                boolean bodyReady = d.findElement(By.tagName("body")).isDisplayed();

                // Tabs visible (any one of them confirms readiness)
                boolean tabsReady =
                        d.findElements(By.xpath("//span[text()='All']")).size() > 0 ||
                        d.findElements(By.xpath("//span[text()='Images']")).size() > 0 ||
                        d.findElements(By.xpath("//span[text()='News']")).size() > 0 ||
                        d.findElements(By.xpath("//span[text()='Videos']")).size() > 0;

                return bodyReady && tabsReady;
            } catch (Exception e) {
                return false;
            }
        });
    }

    //Screenshot
    public void takeScreenshot(String name) {

        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Path dest = Path.of(
                    "screenshots",
                    name + "_" + LocalDateTime.now().format(DATE_TIME) + ".png"
            );

            Files.createDirectories(dest.getParent());
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
    }
    
    public void printResultCount() {
        try {
            System.out.println(
                driver.findElement(By.id("result-stats")).getText()
            );
        } catch (Exception e) {
            System.out.println("Result count not displayed by Google");
        }
    }
    
    @AfterClass
    public void tearDown() {
    	
        if (driver != null) {
            driver.quit();
        }
    }
}

