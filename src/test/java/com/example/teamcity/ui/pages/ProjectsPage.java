package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildTypeElement;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends BasePage {
    private static final String PROJECTS_URL = "/favorite/projects";

    private ElementsCollection projectElements = $$("div[class*='Subproject__container']");
    private SelenideElement header = $(".MainPanel__router--gF > div");
    private ElementsCollection buildTypeElements = $$("div[class*='Subproject__details--fL Subproject__nested--aD']");


    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public ProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public List<ProjectElement> getProjects() {
        return generatePageElements(projectElements, ProjectElement::new);
    }

    public ProjectsPage clickAllProjects() {
        getProjects().forEach(ProjectElement::click);
        return this;
    }

    public List<BuildTypeElement> getBuildTypes() {
        return generatePageElements(buildTypeElements, BuildTypeElement::new);
    }
}
