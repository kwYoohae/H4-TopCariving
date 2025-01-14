package com.backend.topcariving.domain.dto.archive.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "마이카이빙 화면에서 내가 만든 차량에 대한 상세 정보 DTO")
public class PictureSelectedOptionDTO {

	@Schema(description = "선택 옵션 이름", example = "컴포트 II")
	private String name;

	@Schema(description = "선택 옵션 사진", example = "https://www.test.com/car.png")
	private String photoUrl;
}
