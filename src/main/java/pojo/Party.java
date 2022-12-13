package pojo;

public class Party {
    private String partyId;
    private String partyName;

    public Party() {
    }

    public Party(String id, String partyName) {
        this.partyId = id;
        this.partyName = partyName;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String id) {
        this.partyId = id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    @Override
    public String toString() {
        return "Party{" +
                "partyId='" + partyId + '\'' +
                ", partyName='" + partyName + '\'' +
                '}';
    }
}
