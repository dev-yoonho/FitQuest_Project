package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.MonsterRaidDto;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MonsterRaidDao {
    // 몬스터 레이드 생성
    int insertMonsterRaid(MonsterRaidDto raid);

    // 몬스터 레이드 조회
    MonsterRaidDto selectMonsterRaid(int raidId);

    // 특정 파티의 레이드 조회(본인 파티에서 가능)
    List<MonsterRaidDto> selectPartyMonsterRaid(int partyId);

    // 전체 레이드 조회(관리자 전용)
    List<MonsterRaidDto> selectAllMonsterRaid();

    // 몬스터 잔여 체력 업데이트(레이드 중 몬스터 체력 감소)
    int updateMonsterStamina(int raidId, int newStamina);

    // 레이드 완료 일자(레이드 완료시)
    int updateRaidCompletion(int raidId, LocalDateTime raidedAt);

    // 레이드 삭제
    int deleteMonsterRaid(int raidId);
}
