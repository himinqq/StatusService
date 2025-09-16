package com.neves.status.controller;

import com.neves.status.controller.dto.blackbox.BlackboxRegisterRequestDto;
import com.neves.status.controller.dto.blackbox.BlackboxResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Blackbox", description = "블랙박스 관리를 위한 API")
@RestController
@RequestMapping("/blackbox")
public class BlackboxController {
	@Operation(summary="블랙박스 등록", description="특정 유저의 새로운 블랙박스를 등록합니다.")
	@ApiResponses({
			@ApiResponse(responseCode="200", description="블랙박스 등록 성공"),
			@ApiResponse(responseCode="409", description="이미 등록된 블랙박스")
	})
	@PostMapping
	public ResponseEntity<BlackboxResponseDto> register(
			@RequestBody BlackboxRegisterRequestDto request) {
		return ResponseEntity.created(null).body(new BlackboxResponseDto(
				request.getUuid(), request.getUserId(), "2024-06-15T12:34:56Z", "my car blackbox"));
	}

	@Operation(summary="블랙박스 이름 변경", description="등록된 블랙박스의 이름을 변경합니다.")
	@ApiResponses({
			@ApiResponse(responseCode="200", description="블랙박스 이름 변경 성공"),
			@ApiResponse(responseCode="404", description="블랙박스를 찾을 수 없음")
	})
	@PostMapping("/rename")
	public ResponseEntity<BlackboxResponseDto> rename(
			@RequestBody BlackboxRegisterRequestDto request) {
		return ResponseEntity.ok(new BlackboxResponseDto(
				request.getUuid(), request.getUserId(), "2024-06-15T12:34:56Z", "my car blackbox"));
	}

	@Operation(summary="유저의 블랙박스 목록 조회", description="특정 유저가 등록한 모든 블랙박스의 목록을 조회합니다.")
	@ApiResponses({
			@ApiResponse(responseCode="200", description="블랙박스 목록 조회 성공"),
			@ApiResponse(responseCode="404", description="유저를 찾을 수 없음")
	})
	@GetMapping("/{user_id}")
	public ResponseEntity<List<BlackboxResponseDto>> list(@PathVariable("user_id") String userId) {
		return ResponseEntity.ok(List.of(
				new BlackboxResponseDto("123e4567-e89b-12d3-a456-426614174000", userId, "2024-06-15T12:34:56Z", "my car blackbox"),
				new BlackboxResponseDto("223e4567-e89b-12d3-a456-426614174000", userId, "2024-06-16T12:34:56Z", "my second car blackbox")
		));
	}
}
