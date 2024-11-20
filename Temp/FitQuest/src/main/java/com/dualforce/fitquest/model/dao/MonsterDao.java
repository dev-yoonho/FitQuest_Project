package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.MonsterDto;

import java.util.List;

public interface MonsterDao {
    // 몬스터 생성
    int insertMonster(MonsterDto monster);

    // 전체 몬스터 조회
    List<MonsterDto> selectAllMonster();

    // 몬스터 조회(몬스터 이름)
    MonsterDto selectMonsterByName(String monsterName);

    // 몬스터 수정(관리자 전용)
    int updateMonster(MonsterDto monster);

    // 몬스터 삭제(관리자 전용)
    int deleteMonster(String monsterName);
}
