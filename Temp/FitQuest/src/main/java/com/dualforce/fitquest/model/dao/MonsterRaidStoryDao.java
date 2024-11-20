package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.MonsterRaidStoryDto;

import java.util.List;

public interface MonsterRaidStoryDao {
    // 몬스터 레이드 스토리 생성
    int insertMonsterRaidStory(MonsterRaidStoryDto story);

    // 특정 레이드 스토리 전체 조회
    List<MonsterRaidStoryDto> selectStoryByRaidId(int raidId);

    // 특정 스토리 조회
    MonsterRaidStoryDto selectStoryById(int storyId);

    // 스토리 수정(관리자 전용)
    int updateMonsterRaidStory(MonsterRaidStoryDto story);

    // 스토리 삭제
    int deleteMonsterRaidStory(int storyId);

}
