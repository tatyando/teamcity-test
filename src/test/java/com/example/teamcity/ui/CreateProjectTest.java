package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import com.example.teamcity.ui.pages.admin.ProjectPage;
import org.testng.annotations.Test;


public class CreateProjectTest extends BaseUiTest {
    static final String GIT_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @Test(description = "User should be able to create project", groups = {"Positive"})
    public void userCreatesProject() {
        loginAs(testData.getUser());
        CreateProjectPage.open("_Root")
                        .createForm(GIT_URL)
                        .setUpProject(testData.getProject().getName(), testData.getBuildType().getName());

        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECT).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var projectExists = ProjectsPage.open()
                .getProjects().stream()
                .anyMatch(project -> project.getName().text().equals(testData.getProject().getName()));
        softy.assertTrue(projectExists);
    }

    @Test(description = "User should be able to create project and build configuration", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        loginAs(testData.getUser());
        CreateProjectPage.open("_Root")
                .createForm(GIT_URL)
                .setUpProject(testData.getProject().getName(), testData.getBuildType().getName());

        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECT).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);


    }
}
