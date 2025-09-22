package com.supring.specup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandingResponse {

    private List<TagInfo> tagList;
    private List<CsInfo> csList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagInfo {
        private Long tagId;
        private String question;
        private String answer;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CsInfo {
        private Long csId;
        private String csTitle;
        private String time;
        private String csAnswerYN;
    }
}
