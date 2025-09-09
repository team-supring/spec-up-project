package com.supring.specup.service;

import com.supring.specup.dto.PostDto;
import com.supring.specup.dto.PostRequest;

import java.util.List;

public interface RegionBoardService {
    List<PostDto> listAll();

    PostDto get(Long postId);

    PostDto create(String username, PostRequest request);

    void update(Long postId, PostRequest request);

    void delete(Long postId);
}
