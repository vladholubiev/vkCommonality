package ua.samosfator.vk.commonality;

public class PublicPage {
    private String name;
    private String gid;

    public PublicPage(String name, String gid) {
        this.name = name;
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return gid;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublicPage)) return false;

        PublicPage that = (PublicPage) o;

        if (!gid.equals(that.gid)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + gid.hashCode();
        return result;
    }
}
