package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildPage extends CreateBasePage{
    private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";
    protected static SelenideElement urlError = $("#error_url");


    public static CreateBuildPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildPage.class);
    }

    public CreateBuildPage createFormWithUrl(String url) {
        clickCreateFromUrl();
        baseCreateForm(url);
        return this;
    }

    public CreateBuildPage connectionMessage() {
        checkConnectionSuccessfulMessage();
        return this;
    }

    public CreateBuildPage setupProject(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        return this;
    }

    public static void checkEmptyUrlError() {
        urlError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("URL must not be empty"));
    }

    public static void checkEmptyUrlBuildTypeError() {
        checkEmptyUrlError();
    }
}
