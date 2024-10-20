package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;


@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {

        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());

        userCheckRequests.getRequest(Endpoint.BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type is not correct");

    }


    @Test(description = "User should not be able to create two build type with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());

        userCheckRequests.getRequest(Endpoint.BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), Endpoint.BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {

        superUserCheckRequests.getRequest(Endpoint.PROJECT).create(testData.getProject());
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN",
                "p:" + testData.getProject().getId()));

        superUserCheckRequests.<User>getRequest(Endpoint.USERS).create(testData.getUser());

        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckedRequest.getRequest(Endpoint.BUILD_TYPES).create(testData.getBuildType());
        var createdBuildType = userCheckedRequest.<BuildType>getRequest(Endpoint.BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        var project1 = generate(Project.class);
        superUserCheckRequests.getRequest(Endpoint.PROJECT).create(project1);

        var project2 = generate(Project.class);
        superUserCheckRequests.getRequest(Endpoint.PROJECT).create(project2);

        var user1 = generate(User.class);
        user1.setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + project1.getId()));
        superUserCheckRequests.<User>getRequest(Endpoint.USERS).create(user1);

        var user2 = generate(User.class);
        user2.setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + project2.getId()));
        superUserCheckRequests.<User>getRequest(Endpoint.USERS).create(user2);

        testData.getBuildType().setProject(project1);
        new UncheckedBase(Specifications.authSpec(user2), Endpoint.BUILD_TYPES)
                .create(testData.getBuildType())
                .then().assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("Access denied. Check the user has enough permissions to perform the operation."));
        }
    }
