package com.example.teamcity.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;


public abstract class BasePageElement {
    private final SelenideElement element;

    public BasePageElement(SelenideElement element) {
        this.element = element;
    }

    protected SelenideElement find(By selector) {
        return element.find(selector);
    }

    protected SelenideElement find(String cssSelector) {
        return element.find(cssSelector);
    }

    protected ElementsCollection findAll(By selector) {
        return element.findAll(selector);
    }

    protected ElementsCollection findAll(String cssSelector) {
        return element.findAll(cssSelector);
    }

    public void click() {
        element.click();
    }
}
