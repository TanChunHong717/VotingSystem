package pojo;

import java.util.List;

public class Constituency {
    private Integer constituencyId;
    private String daerah;
    private String negeri;
    private Integer electionId;
    private List<Candidate> candidateList;
    private List<Voter> voterList;

    public Constituency() {
    }

    public Constituency(Integer constituencyId, String daerah, String negeri, Integer electionId, List<Candidate> candidateList, List<Voter> voterList) {
        this.constituencyId = constituencyId;
        this.daerah = daerah;
        this.negeri = negeri;
        this.electionId = electionId;
        this.candidateList = candidateList;
        this.voterList = voterList;
    }

    public Integer getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(Integer constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getDaerah() {
        return daerah;
    }

    public void setDaerah(String daerah) {
        this.daerah = daerah;
    }

    public String getNegeri() {
        return negeri;
    }

    public void setNegeri(String negeri) {
        this.negeri = negeri;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<Candidate> candidateList) {
        this.candidateList = candidateList;
    }

    public List<Voter> getVoterList() {
        return voterList;
    }

    public void setVoterList(List<Voter> voterList) {
        this.voterList = voterList;
    }

    @Override
    public String toString() {
        return "Constituency{" +
                "constituencyId=" + constituencyId +
                ", daerah='" + daerah + '\'' +
                ", negeri='" + negeri + '\'' +
                ", electionId=" + electionId +
                ", candidateList=" + candidateList +
                ", voterList=" + voterList +
                '}';
    }
}
