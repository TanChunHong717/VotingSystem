package pojo;

import java.util.List;

public class Election {
    private Integer electionId;
    private String electionName;
    private List<Constituency> constituencyList;

    public Election() {
    }

    public Election(Integer id, String electionName, List<Constituency> constituencyList) {
        this.electionId = id;
        this.electionName = electionName;
        this.constituencyList = constituencyList;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    @Override
    public String toString() {
        return "Election{" +
                "electionId=" + electionId +
                ", electionName='" + electionName + '\'' +
                ", constituencyList=" + constituencyList +
                '}';
    }
}
