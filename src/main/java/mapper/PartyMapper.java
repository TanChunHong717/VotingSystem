package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Party;

public interface PartyMapper {
    public Party getPartyById(@Param("partyId") Integer partyId);
}