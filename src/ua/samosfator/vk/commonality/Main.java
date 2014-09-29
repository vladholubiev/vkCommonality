package ua.samosfator.vk.commonality;

public class Main {

    public static void main(String[] args) throws Exception {
        Group pzas = new Group("76497135");
        pzas.countTopSubscriptions();
        pzas.print();
    }
}
