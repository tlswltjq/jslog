package com.jslog_spring.member.interfaces.rest.dto;

public record EditProfileResponse(
        Long memberId,
        String bio
) {
    public static EditProfileResponse of(Long memberId, String bio) {
        return new EditProfileResponse(memberId, bio);
    }
}
