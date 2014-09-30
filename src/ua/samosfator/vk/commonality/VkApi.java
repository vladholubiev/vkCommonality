package ua.samosfator.vk.commonality;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class VkApi {
    public static List<String> getMembers(String groupId) throws Exception {
        List<String> members = new ArrayList<>();
        String callUrl = URL.getGroupsMembersURL(groupId);
        JSONArray response = getUsersResponse(callUrl);
        for (Object o : response) {
            members.add(o.toString());
        }
        return members;
    }

    public static List<PublicPage> getUserSubscriptions(String uid) throws Exception {
        String callUrl = URL.getUserSubscriptionsURL(uid);
        JSONArray subscriptionsResponse = getSubscriptionsResponse(callUrl);
        List<PublicPage> publicPages = new ArrayList<>();

        for (Object o : subscriptionsResponse) {
            String name = (String) ((JSONObject) o).get("name");
            String id = String.valueOf(((JSONObject) o).get("gid"));
            //null is when subscription is a person
            if (name != null && id != null) publicPages.add(new PublicPage(name, id));
        }
        return publicPages;
    }

    public static List<String> getUserArtists(String uid) throws Exception {
        String callUrl = URL.getUserAudiosURL(uid);
        List audiosResponse = getAudiosResponse(callUrl);
        List<String> audios = new ArrayList<>();

        for (Object o : audiosResponse) {
            String artist = (String) ((JSONObject) o).get("artist");
            if (artist != null && !audios.contains(artist.toLowerCase())) audios.add(artist.toLowerCase());
        }
        return audios;
    }

    private static String getJSON(String url) throws IOException, URISyntaxException {
        String json;

        HttpGet httpget = new HttpGet(url);
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse response;

        response = httpclient.execute(httpget);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            json = IOUtils.toString(entity.getContent());
        } else throw new NullPointerException();

        return json;
    }

    private static JSONArray getUsersResponse(String callUrl) throws Exception {
        String json = getJSON(callUrl);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) ((JSONObject) jsonParser.parse(json)).get("response");
        return (JSONArray) jsonObject.get("users");
    }

    private static List getAudiosResponse(String callUrl) throws Exception {
        String json = getJSON(callUrl);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) ((JSONObject) jsonParser.parse(json)).get("response");
        return jsonArray != null ? jsonArray.subList(1, jsonArray.size()) : new JSONArray();
    }

    public static JSONArray getSubscriptionsResponse(String callUrl) throws Exception {
        String json = getJSON(callUrl);
        return (JSONArray) ((JSONObject) new JSONParser().parse(json)).get("response");
    }
}
