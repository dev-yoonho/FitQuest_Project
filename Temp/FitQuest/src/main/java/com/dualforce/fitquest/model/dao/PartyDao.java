package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.PartyDto;

import java.util.List;

public interface PartyDao {
    // 파티 생성
    int insertParty(PartyDto party);

    // 전체 파티 조회
    List<PartyDto> selectAllParty();

    // 파티 조회(파티 이름)
    PartyDto selectPartyByName(String partyName);

    // 파티 삭제
    int deleteParty(int partyId);
}
