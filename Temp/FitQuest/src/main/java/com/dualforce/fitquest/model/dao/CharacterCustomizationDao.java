package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.CharacterCustomizationDto;
import com.dualforce.fitquest.model.dto.IllustrationDto;
import com.dualforce.fitquest.model.dto.VoiceDto;

import java.util.List;

public interface CharacterCustomizationDao {
    // 일러스트만 추가
    int insertUserIllustration(int userId, int illustrationId);

    // 보이스만 추가
    int insertUserVoice(int userId, int voiceId);

    // 커스터마이징 옵션(일러스트 + 보이스) 추가
    int insertUserCustom(CharacterCustomizationDto custom);

    // 보유 일러스트 조회(사용자 ID)
    List<IllustrationDto> selectUserIllustration(int userId);

    // 보유 보이스 조회(사용자 ID)
    List<VoiceDto> selectUserVoice(int userId);

    // 커스터마이징 옵션 조회(사용자 ID)
    CharacterCustomizationDto selectUserCustom(int userId);

    // 커스터마이징 옵션 수정
    int updateUserCustom(CharacterCustomizationDto custom);

    // 커스터마이징 옵션 초기화
    int resetUserCustom(int userId);

}
