package pojo;


import java.util.Arrays;
import java.util.List;

public class Voter {
    public static final Character MALE = 'M';
    public static final Character FEMALE = 'F';

    private String ic;
    private String name;
    private Character gender;
    private String address;
    private byte[] fingerprint;
    private Integer constituencyId;
    private List<Election> votedElectionList;

    public Voter() {
    }

    public Voter(String ic, String name, Character gender, String address, byte[] fingerprint, Integer constituencyId, List<Election> votedElectionList) {
        this.ic = ic;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.fingerprint = fingerprint;
        this.constituencyId = constituencyId;
        this.votedElectionList = votedElectionList;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(byte[] fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Integer getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(Integer constituencyId) {
        this.constituencyId = constituencyId;
    }

    public List<Election> getVotedElectionList() {
        return votedElectionList;
    }

    public void setVotedElectionList(List<Election> votedElectionList) {
        this.votedElectionList = votedElectionList;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "ic='" + ic + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", fingerprint=" + Arrays.toString(fingerprint) +
                ", constituencyId=" + constituencyId +
                ", votedElectionList=" + votedElectionList +
                '}';
    }
}
