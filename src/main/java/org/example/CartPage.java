package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private
    WebDriver driver;

    private By backpackInCart = By.xpath("//div[text()='Sauce Labs Backpack']");
    private By boltTShirtInCart = By.xpath("//div[text()='Sauce Labs Bolt T-Shirt']");
    private By onesieInCart = By.xpath("//div[text()='Sauce Labs Onesie']");
    private By bikeLightInCart = By.xpath("//div[text()='Sauce Labs Bike Light']");
    private By removeBackpackButton = By.id("remove-sauce-labs-backpack");
    private By removeBoltTShirtButton = By.id("remove-sauce-labs-bolt-t-shirt");
    private By cartButton = By.className("shopping_cart_link");
    private By removeFleeceJacketButton = By.id("remove-sauce-labs-fleece-jacket");
    private By removeSauceOnesieButton = By.id("remove-sauce-labs-onesie");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isBackpackInCart() {
        return driver.findElements(backpackInCart).size() > 0;
    }

    public boolean isBoltTShirtInCart() {
        return driver.findElements(boltTShirtInCart).size() > 0;
    }

    public boolean isOnesieInCart() {
        return driver.findElements(onesieInCart).size() > 0;
    }

    public boolean isBikeLightInCart() {
        return driver.findElements(bikeLightInCart).size() > 0;
    }

    public void removeBackpackFromCart() {
        driver.findElement(removeBackpackButton).click();
    }

    public void removeBoltTShirtFromCart() {
        driver.findElement(removeBoltTShirtButton).click();
    }

    public CartPage goToCart() {
        driver.findElement(cartButton).click();
        return new CartPage(driver);
    }

    public boolean isProductInCart(String productName) {
        By productLocator = By.xpath("//div[text()='" + productName + "']");
        return driver.findElements(productLocator).size() > 0;
    }
    public void removeFleeceJacketFromCart() {
        driver.findElement(removeFleeceJacketButton).click();
    }
    public void removeSauceOnesieButton() {
        driver.findElement(removeSauceOnesieButton).click();
    }


}
