package ua.samosfator.vk.commonality;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class URL {
    public static String getGroupsMembersURL(String groupId) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https").setHost("api.vk.com")
                .setPath("/method/groups.getMembers")
                .addParameter("access_token", Credentials.VK_ACCESS_TOKEN)
                .addParameter("group_id", groupId);
        return uriBuilder.build().toString();
    }

    public static String getUserSubscriptionsURL(String uid) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https").setHost("api.vk.com")
                .setPath("/method/users.getSubscriptions")
                .addParameter("extended", "1")
                .addParameter("count", "200")
                .addParameter("user_id", uid);
        return uriBuilder.build().toString();
    }

    public static String getUserAudiosURL(String uid) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https").setHost("api.vk.com")
                .setPath("/method/audio.get")
                .addParameter("access_token", Credentials.VK_ACCESS_TOKEN)
                .addParameter("owner_id", uid)
                .addParameter("count", "6000");
        return uriBuilder.build().toString();
    }
}
