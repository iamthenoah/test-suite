package net.greffnoah.definition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.greffnoah.clients.service.ClipboardService;
import net.greffnoah.clients.model.Record;
import net.greffnoah.util.http.Response;

import java.util.Objects;

public class ClipboardLookup {

    private String userId;
    private Response<ClipboardService.Records> response;

    @Given("A user id {string}")
    public void aValidUser(String userId) {
        this.userId = userId;
    }

    @When("Clipboard records are fetched")
    public void clipboardRecordsAreFetched() throws Exception {
        this.response = ClipboardService.getUserClipboardRecords(userId);
    }

    @Then("A list of clipboard records is returned")
    public void aListOfClipboardRecordsIsReturned() throws Exception {
        for (Record record : response.body) {
            if (!Objects.equals(record.userId, userId)) {
                throw new Exception("A clipboard record was not from the given " + userId + ".");
            }
        }
        System.out.println("Clipboard records are returned");
    }

    @Then("A not found http exception is returned")
    public void aNotFoundHttpExceptionIsReturned() throws Exception {
        if (response.response.getStatusLine().getStatusCode() != 404) {
            throw new Exception("Clipboard records were found while none were excepted given user " + userId + ".");
        }
        System.out.println("Clipboard records are not returned");
    }
}
