package com.supring.specup.service;

import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;

import java.util.List;

public interface CsService {
    CsDto get(Long csId);

    CsDto getIfOwner(String username, Long csId);

    List<CsDto> listAll();

    List<CsDto> listByOwner(String username);

    CsDto create(String username, CsRequest request);

    void update(Long csId, CsRequest request);

    void delete(Long csId);
}
