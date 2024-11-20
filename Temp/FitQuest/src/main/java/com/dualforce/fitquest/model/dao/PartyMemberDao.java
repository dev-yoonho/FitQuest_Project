package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.PartyMemberDto;

import java.util.List;

public interface PartyMemberDao {
    // 파티 멤버 추가
    int insertPartyMember(PartyMemberDto member);

    // 파티 멤버 전체 조회
    List<PartyMemberDto> selectAllPartyMember(int partyId);

    // 닉네임으로 파티 멤버 삭제
    int deletePartyMember(String userNickname);
}
