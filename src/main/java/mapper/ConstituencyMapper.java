package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Constituency;

import java.util.List;

public interface ConstituencyMapper {
    public Constituency getConstituencyById(@Param("constituencyId") Integer constituencyId);
    public List<Constituency> getConstituencyInvolvedInElection(@Param("electionId") Integer electionId);
}
