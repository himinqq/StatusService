package com.neves.status.controller.dto.blackbox;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "블랙박스 이름 변경 요청 DTO")
@Getter @Setter
@AllArgsConstructor
public class BlackboxRenameRequestDto {
	@Schema(description = "블랙박스 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
	private final String uuid;
	@Schema(description = "블랙박스 닉네임", example = "my car blackbox")
	private final String nickname;
}
