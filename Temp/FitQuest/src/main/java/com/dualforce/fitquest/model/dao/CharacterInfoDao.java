package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.CharacterInfoDto;

import java.util.List;

public interface CharacterInfoDao {
    // 캐릭터 생성
    int insertCharacterInfo(CharacterInfoDto characterInfo);

    // 캐릭터 조회(사용자 ID)
    CharacterInfoDto selectCharacterInfoByUserId(int userId);

    // 전체 캐릭터 조회(관리자 전용)
    List<CharacterInfoDto> selectAllCharacterInfo();

    // 캐릭터 스탯 수정
    int updateCharacterInfo(CharacterInfoDto characterInfo);
    
    // 캐릭터 삭제
    int deleteCharacterInfo(int userId);

    // 기능 추가 필요
}
