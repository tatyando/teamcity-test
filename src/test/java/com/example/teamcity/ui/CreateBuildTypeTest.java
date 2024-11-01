package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateBuildPage;
import org.testng.annotations.Test;

import static com.example.teamcity.ui.CreateProjectTest.GIT_URL;

public class CreateBuildTypeTest extends BaseUiTest {

    @Test(description = "User should be able to create project", groups = {"Positive"})
    public void userCreatesProject() {
        loginAs(testData.getUser());

        CreateBuildPage.open(testData.getProject().getId()).createFormWithUrl(GIT_URL).connectionMessage()
                .setupProject(testData.getBuildType().getName());

        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
        softy.assertEquals(createdBuildType.getProject().getId(),testData.getProject().getId());

        var buildTypeExists = ProjectsPage.open().clickAllProjects().getBuildTypes().stream()
                .anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(buildTypeExists);
        softy.assertEquals(createdBuildType.getProject().getId(),testData.getProject().getId());
    }
    @Test(description = "User should not be able to create a build type with empty Url", groups = {"Positive"})
    public void userCreatesProjectWithEmptyUrl() {
        loginAs(testData.getUser());

        int count = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();

        CreateBuildPage.open(testData.getProject().getId()).createFormWithUrl("");
        CreateBuildPage.checkEmptyUrlBuildTypeError();

        int newCount = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();
        softy.assertEquals(newCount,count);
    }
}
