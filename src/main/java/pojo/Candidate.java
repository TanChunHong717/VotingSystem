package pojo;

public class Candidate {
    private Integer candidateId;
    private Voter voter;
    private Integer constituencyId;
    private Integer electionId;
    private Party party;
    private Integer voteNumber;

    public Candidate() {
    }

    public Candidate(Integer candidateId, Voter voter, Integer constituencyId, Integer electionId, Party party, Integer voteNumber) {
        this.candidateId = candidateId;
        this.voter = voter;
        this.constituencyId = constituencyId;
        this.electionId = electionId;
        this.party = party;
        this.voteNumber = voteNumber;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public Integer getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(Integer constituencyId) {
        this.constituencyId = constituencyId;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Integer getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(Integer voteNumber) {
        this.voteNumber = voteNumber;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "candidateId=" + candidateId +
                ", voter=" + voter +
                ", constituencyId=" + constituencyId +
                ", electionId=" + electionId +
                ", party=" + party +
                ", voteNumber=" + voteNumber +
                '}';
    }
}
