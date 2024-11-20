package com.dualforce.fitquest.model.dao;

import com.dualforce.fitquest.model.dto.IllustrationDto;

import java.util.List;

public interface IllustrationDao {
    // 일러스트 등록(관리자 전용)
    int insertIllustration(IllustrationDto illustration);

    // 일러스트 조회(일러스트 ID)(관리자 전용)
    IllustrationDto selectIllustrationById(int illustrationId);

    // 일러스트 전체 조회(관리자 전용)
    List<IllustrationDto> selectAllIllustration();

    // 일러스트 수정(관리자 전용)
    int updateIllustration(IllustrationDto illustration);

    // 일러스트 삭제(관리자 전용)
    int deleteIllustration(int illustrationId);
}
