package net.greffnoah.definition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.greffnoah.clients.service.ClipboardServiceFacade;
import net.greffnoah.clients.model.Record;

import java.util.List;
import java.util.Objects;

public class ClipboardLookupStepDefinitions {

    private String userId;
    private List<Record> records;

    @Given("A user id {string}")
    public void aValidUser(String userId) {
        this.userId = userId;
    }

    @When("Clipboard records are fetched")
    public void clipboardRecordsAreFetched() throws Exception {
        this.records = ClipboardServiceFacade.getUserClipboardRecords(userId);
    }

    @Then("A list of clipboard records is returned")
    public void aListOfClipboardRecordsIsReturned() throws Exception {
        for (Record record : records) {
            if (!Objects.equals(record.userId, userId)) {
                throw new Exception("A clipboard record was not from the given " + userId + ".");
            }
        }
    }

    @Then("A not found http exception is returned")
    public void aNotFoundHttpExceptionIsReturned() throws Exception {
        if (records != null) {
            throw new Exception("Clipboard records were found while none were excepted given user " + userId + ".");
        }
    }
}
