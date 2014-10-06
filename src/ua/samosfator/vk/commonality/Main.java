package ua.samosfator.vk.commonality;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Group pzas = new Group("15894490");
        Map<Object, Object> top = pzas.countTopArtists();
        pzas.printTopArtists(top);
    }
}
