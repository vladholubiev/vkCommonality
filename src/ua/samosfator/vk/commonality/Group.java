package ua.samosfator.vk.commonality;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Group {
    private String groupId;
    private Map<PublicPage, Integer> subscriptionsCount = new HashMap<>();

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
        subscriptionsCount.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new)).forEach(subscriptionsCount::put);
    }

    //TODO Implement different printing styles
    //Now prints in VK style: @club123(PublicPageName)
    public void print() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("result.txt"));
        for (Map.Entry<PublicPage, Integer> entry : subscriptionsCount.entrySet()) {
            pw.println(entry.getValue() + " - @club" + entry.getKey().getGroupId() + "(" + entry.getKey() + ")");
        }
        pw.flush();
        pw.close();
    }
}
