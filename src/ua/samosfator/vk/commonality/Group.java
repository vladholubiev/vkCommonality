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

    public void countTopSubscriptions() throws Exception {
        List<String> groupMembers = VkApi.getMembers(groupId);
        List<PublicPage> userSubscriptions = new ArrayList<>();

        for (String groupMember : groupMembers) {
            userSubscriptions.addAll(VkApi.getUserSubscriptions(groupMember));
            System.out.println("Processing: " + groupMember);
        }
        for (PublicPage pp : userSubscriptions) {
            int occurrences = Collections.frequency(userSubscriptions, pp);
            subscriptionsCount.put(pp, occurrences);
        }

        //sort map by value
        subscriptionsCount = subscriptionsCount.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void countTopArtists() throws Exception {
        List<String> groupMembers = VkApi.getMembers(groupId);
        List<String> userAudios = new ArrayList<>();

        for (String groupMember : groupMembers) {
            userAudios.addAll(VkApi.getUserArtists(groupMember));
            System.out.println("Processing: " + groupMember);
        }
        for (String artist : userAudios) {
            int occurrences = Collections.frequency(userAudios, artist);
            artistCount.put(artist, occurrences);
        }

        //sort map by value
        artistCount = artistCount.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
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

    public void printTopArtists() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("result.txt"));
        for (Map.Entry<String, Integer> entry : artistCount.entrySet()) {
            pw.println(entry.getValue() + " - " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entry.getKey()));
        }
        pw.flush();
        pw.close();
    }
}
