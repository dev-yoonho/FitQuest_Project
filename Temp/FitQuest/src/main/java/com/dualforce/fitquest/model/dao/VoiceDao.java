package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.VoiceDto;

import java.util.List;

public interface VoiceDao {
    // 일러스트 등록(관리자 전용)
    int insertVoice(VoiceDto voice);

    // 일러스트 조회(일러스트 ID)(관리자 전용)
    VoiceDto selectVoiceById(int voiceId);

    // 일러스트 전체 조회(관리자 전용)
    List<VoiceDto> selectAllVoice();

    // 일러스트 수정(관리자 전용)
    int updateVoice(VoiceDto voice);

    // 일러스트 삭제(관리자 전용)
    int deleteVoice(int voiceId);
}
