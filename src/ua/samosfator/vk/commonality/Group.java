package ua.samosfator.vk.commonality;

import com.google.common.base.CaseFormat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Group {
    private String groupId;
    private Map<PublicPage, Integer> subscriptionsCount = new HashMap<>();
    private Map<String, Integer> artistCount = new HashMap<>();

    public Group(String groupId) {
        this.groupId = groupId;
    }

    public Map<Object, Object> countTopSubscriptions() throws Exception {
        List<String> groupMembers = VkApi.getMembers(groupId);




        return new Counter(groupMembers).countTopSubscriptions();
    }

    public Map<Object, Object> countTopArtists() throws Exception {
        List<String> groupMembers = VkApi.getMembers(groupId);
        return new Counter(groupMembers).countArtists();
    }

    //TODO Implement different printing styles
    //Now prints in VK style: @club123(PublicPageName)
    public void printClubs() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("result.txt"));
        for (Map.Entry<PublicPage, Integer> entry : subscriptionsCount.entrySet()) {
            pw.println(entry.getValue() + " - @club" + entry.getKey().getGroupId() + "(" + entry.getKey() + ")");
        }
        pw.flush();
        pw.close();
    }

    public void printTopArtists(Map<Object, Object> map) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("result.txt"));
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pw.println(entry.getValue() + " - " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entry.getKey()));
        }
        pw.flush();
        pw.close();
    }
}
