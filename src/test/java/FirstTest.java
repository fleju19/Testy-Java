import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class FirstTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private AlertExample alertExample;


    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        alertExample = new AlertExample(driver);


    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void testLoginPositive() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isLoggedIn());
    }
    @Test
    public void testLoginNegativeUsername() {
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertFalse(loginPage.isLoggedIn());
    }
    @Test
    public void testLoginNegativePassword() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_saucee");
        loginPage.clickLoginButton();
        Assert.assertFalse(loginPage.isLoggedIn());
    }    @Test
    public void testLoginNegativeUsernamePassword() {
        loginPage.enterUsername("standard_useeer");
        loginPage.enterPassword("secret_sauceeee");
        loginPage.clickLoginButton();
        Assert.assertFalse(loginPage.isLoggedIn());
    }





    @Test
    public void testAddItemsToCartAndRemoveBackpackAndBoltTShirt() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.addBackpackToCart();
        inventoryPage.addBoltTShirtToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isBackpackInCart());
        Assert.assertTrue(cartPage.isBoltTShirtInCart());

        cartPage.removeBackpackFromCart();
        cartPage.removeBoltTShirtFromCart();

        Assert.assertFalse(cartPage.isBackpackInCart());
        Assert.assertFalse(cartPage.isBoltTShirtInCart());
    }

    @Test
    public void testAddItemsToCartNegative() {
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.addBackpackToCart();
        inventoryPage.addBoltTShirtToCart();
        cartPage.goToCart();
        Assert.assertTrue(cartPage.isBackpackInCart());
        Assert.assertFalse(cartPage.isBoltTShirtInCart());
    }

    @Test
    public void testAddItemsToCartPositive() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.addBackpackToCart();
        inventoryPage.addBoltTShirtToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isBackpackInCart());
        Assert.assertTrue(cartPage.isBoltTShirtInCart());
    }

    @Test
    public void testCheckoutPositive() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.addBackpackToCart();
        inventoryPage.addBoltTShirtToCart();
        cartPage.goToCart();
        checkoutPage.goToCheckout();

        checkoutPage.enterFirstName("Jan");
        checkoutPage.enterLastName("Kowalski");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete());
    }
    @Test
    public void testCheckoutNegative() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.addBackpackToCart();
        inventoryPage.addBoltTShirtToCart();
        cartPage.goToCart();
        checkoutPage.goToCheckout();


        checkoutPage.enterFirstName("");
        checkoutPage.enterLastName("");
        checkoutPage.enterPostalCode("");
        checkoutPage.clickContinue();
        Assert.assertTrue(checkoutPage.isErrorMessage());
    }

    @Test
    public void testAddOnesieAndBikeLightToCart() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.addOnesieToCart();
        cartPage.goToCart();
        Assert.assertTrue(cartPage.isOnesieInCart());

        driver.navigate().back();
        inventoryPage.addBikeLightToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isBikeLightInCart());
        Assert.assertTrue(cartPage.isOnesieInCart());

        checkoutPage.goToCheckout();

        checkoutPage.enterFirstName("Jan");
        checkoutPage.enterLastName("Marcin");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete());
    }

    @Test
    public void testSortByLowestPriceAndCheckoutNegative() {
        loginPage.enterUsername("error_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.sortProductsBy("Price (low to high)");
        alertExample.handleAlert(driver);
        String lowestPriceProductName = inventoryPage.getFirstProductName();
        String lowestPrice = inventoryPage.getFirstProductPrice();
        inventoryPage.addFirstProductToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(lowestPriceProductName));


        checkoutPage.goToCheckout();
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("34/12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertFalse(checkoutPage.isOrderComplete());

    }

    @Test
    public void testSortByLowestPriceAndCheckout1Positive() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.sortProductsBy("Price (low to high)");

        String lowestPriceProductName = inventoryPage.getFirstProductName();
        String lowestPrice = inventoryPage.getFirstProductPrice();
        inventoryPage.addFirstProductToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(lowestPriceProductName));


        checkoutPage.goToCheckout();
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("/34/12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete());

    }


    @Test
    public void testSortByNameAndSelectMultipleProducts() {
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        inventoryPage.sortProductsBy("Name (Z to A)");

        String firstProductName = inventoryPage.getFirstProductName();
        String lastProductName = inventoryPage.getLastProductName();
        inventoryPage.addFirstProductToCart();
        inventoryPage.addLastProductToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(firstProductName));
        Assert.assertTrue(cartPage.isProductInCart(lastProductName));

        driver.navigate().back();
        inventoryPage.sortProductsBy("Price (high to low)");
        String highestPriceProductName = inventoryPage.getFirstProductName();
        inventoryPage.addFirstProductToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(highestPriceProductName));

        checkoutPage.goToCheckout();
        checkoutPage.enterFirstName("Paweł");
        checkoutPage.enterLastName("Kamiński");
        checkoutPage.enterPostalCode("45/12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete());

    }  @Test
    public void testSortByPriceAndCheckoutPositive() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        inventoryPage.sortProductsBy("Price (low to high)");
        String lowestPriceProductName = inventoryPage.getFirstProductName();
        String lowestPrice = inventoryPage.getFirstProductPrice();
        inventoryPage.addFirstProductToCart();
        String highestPriceProductName = inventoryPage.getLastProductName();
        String highestPrice = inventoryPage.getLastProductPrice();
        inventoryPage.addLastProductToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(lowestPriceProductName));
        Assert.assertTrue(cartPage.isProductInCart(highestPriceProductName));

        cartPage.removeFleeceJacketFromCart();

        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Fleece Jacket"));

        driver.navigate().back();

        inventoryPage.sortProductsBy("Price (high to low)");

        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".inventory_item .btn_inventory"));
        addToCartButtons.get(1).click();
        String secondHighestPriceProductName = inventoryPage.getFirstProductName();

        cartPage.goToCart();

        Assert.assertTrue(cartPage.isBackpackInCart());

        checkoutPage.goToCheckout();
        checkoutPage.enterFirstName("Jan");
        checkoutPage.enterLastName("Drobny");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete());
    }
    @Test
    public void testSortByPriceAndCheckoutNegative() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        inventoryPage.sortProductsBy("Price (low to high)");
        String lowestPriceProductName = inventoryPage.getFirstProductName();
        String lowestPrice = inventoryPage.getFirstProductPrice();
        inventoryPage.addFirstProductToCart();
        String highestPriceProductName = inventoryPage.getLastProductName();
        String highestPrice = inventoryPage.getLastProductPrice();
        inventoryPage.addLastProductToCart();
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(lowestPriceProductName));
        Assert.assertTrue(cartPage.isProductInCart(highestPriceProductName));


        cartPage.removeFleeceJacketFromCart();

        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Fleece Jacket"));


        driver.navigate().back();
        inventoryPage.sortProductsBy("Price (high to low)");

        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".inventory_item .btn_inventory"));
        addToCartButtons.get(1).click();
        String secondHighestPriceProductName = inventoryPage.getFirstProductName();

        cartPage.goToCart();

        Assert.assertFalse(cartPage.isProductInCart(secondHighestPriceProductName));

        checkoutPage.goToCheckout();
        checkoutPage.enterFirstName("Jan");
        checkoutPage.enterLastName("Drobny");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete());
    }

    @Test
    public void testSortAndRemoveProduct() {

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        inventoryPage.sortProductsBy("Price (high to low)");
        String highestPriceProductName = inventoryPage.getFirstProductName();
        inventoryPage.addFirstProductToCart();
        inventoryPage.addBoltTShirtToCart();
        inventoryPage.addOnesieToCart();

        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(highestPriceProductName));
        Assert.assertTrue(cartPage.isBoltTShirtInCart());
        Assert.assertTrue(cartPage.isOnesieInCart());

        cartPage.removeSauceOnesieButton();

        Assert.assertFalse(cartPage.isOnesieInCart());

        driver.navigate().back();

        String lastProductName = inventoryPage.getLastProductName();
        inventoryPage.addLastProductToCart();

        cartPage.goToCart();
        Assert.assertTrue(cartPage.isProductInCart(lastProductName));

    }


}





