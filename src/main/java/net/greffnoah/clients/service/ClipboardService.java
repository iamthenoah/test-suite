package net.greffnoah.clients.service;

import net.greffnoah.clients.model.Record;
import net.greffnoah.util.http.HttpClientHelper;
import net.greffnoah.util.http.HttpRequestType;
import net.greffnoah.util.http.Response;

import java.util.ArrayList;

public class ClipboardService {

    public static final class Records extends ArrayList<Record> {}

    private static final String CLIPBOARD_API_SERVICE_URL = "http://UniclipClusterLB-Staging-a0df58daff31227f.elb.eu-north-1.amazonaws.com:4000";

    public static Response<Records> getUserClipboardRecords(String userId) throws Exception {
        String url = CLIPBOARD_API_SERVICE_URL + "/fetch/" + userId;
        return HttpClientHelper.performRequest(HttpRequestType.GET, url, null, Records.class);
    }
}
