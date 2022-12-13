package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Voter;

public interface VoterMapper {
    public Voter getVoterByIc(@Param("ic") String ic);
    public Voter getVoterByConstituency(@Param("constituencyId") String constituencyId);
    public void vote(@Param("ic") String ic, @Param("electionId") Integer electionId);
}
