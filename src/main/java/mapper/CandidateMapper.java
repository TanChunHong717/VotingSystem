package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Candidate;

import java.util.List;

public interface CandidateMapper {
    public Candidate getCandidateById(@Param("candidateId") Integer candidateId);
    public List<Candidate> getCandidateByElectionAndConstituency(@Param("electionId") Integer electionId, @Param("constituencyId") Integer constituencyId);
}
