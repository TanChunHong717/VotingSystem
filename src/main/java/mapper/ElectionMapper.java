package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Election;

import java.util.List;

public interface ElectionMapper {
    public Election getElectionById(@Param("electionId") Integer electionId);
    public List<Election> getVotedElection(@Param("ic") String ic);
}
