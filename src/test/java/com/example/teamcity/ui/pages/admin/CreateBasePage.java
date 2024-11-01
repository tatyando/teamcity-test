package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBasePage extends BasePage {
    protected static final String CREATE_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=%s";

    protected SelenideElement urlInput = $("#url");
    protected SelenideElement submitButton = $(Selectors.byAttribute("value", "Proceed"));
    protected SelenideElement buildTypeNameInput = $("#buildTypeName");
    protected SelenideElement connectionSuccessfulMessage = $(".connectionSuccessful");
    protected SelenideElement createFromUrlLink = $("a[href = '#createFromUrl']");
    protected SelenideElement createManuallyLink = $("a[href = '#createManually']");


    protected void baseCreateForm(String url) {
        urlInput.val(url);
        submitButton.click();
        connectionSuccessfulMessage.should(Condition.appear, BASE_WAITING);
    }

    protected void clickCreateFromUrl() {
        createFromUrlLink.click();
    }

    protected void checkConnectionSuccessfulMessage() {
        connectionSuccessfulMessage.should(Condition.visible, BASE_WAITING);
    }

    protected void switchToCreateManually() {
        createManuallyLink.click();
    }
}
