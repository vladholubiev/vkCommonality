package ua.samosfator.vk.commonality;

import java.util.*;
import java.util.stream.Collectors;

public class Counter {
    private Map<Object, Object> topListing = new HashMap<>();
    private List<String> users = new ArrayList<>();

    public Counter(List<String> users) {
        this.users = users;
    }

    public Map<Object, Object> countTopSubscriptions(List<String> users) throws Exception {
        List<PublicPage> userSubscriptions = new ArrayList<>();
        for (String user : users) {
            userSubscriptions.addAll(VkApi.getUserSubscriptions(user));
            System.out.println("Processing: " + user);
        }
        for (PublicPage pp : userSubscriptions) {
            int occurrences = Collections.frequency(userSubscriptions, pp);
            topListing.put(pp, occurrences);
        }

        //sort map by value
        return topListing.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }


    public Map<Object, Object> countArtists() throws Exception {
        List<String> userAudios = new ArrayList<>();

        for (String user : users) {
            userAudios.addAll(VkApi.getUserArtists(user));
            System.out.println("Processing: " + user);
        }
        for (String artist : userAudios) {
            int occurrences = Collections.frequency(userAudios, artist);
            topListing.put(artist, occurrences);
        }

        //sort map by value
        return topListing.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

    }
}