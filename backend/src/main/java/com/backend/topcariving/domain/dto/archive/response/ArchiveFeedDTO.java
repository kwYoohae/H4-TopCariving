package com.backend.topcariving.domain.dto.archive.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.entity.archive.CarArchiving;
import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;
import com.backend.topcariving.domain.entity.review.CarReview;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "아카이빙 - 피드에 출력되는 DTO")
public class ArchiveFeedDTO {

	@Schema(description = "차량 아카이빙 ID", example = "1")
	private Long archivingId;

	@Schema(description = "차량 옵션들")
	private Map<String, List<String>> carArchiveResult;

	@Schema(description = "시승 및 구매 일시", example = "2023-08-01 12:00:00")
	private LocalDateTime dayTime;

	@Schema(description = "차량 전체 텍스트 리뷰", example = "너무 좋아요~~")
	private String carReview;

	@Schema(description = "차량 전체 태그 리뷰")
	private List<TagResponseDTO> tags;

	@Schema(description = "시승, 구매 종류", example = "시승")
	private ArchivingType type;

	public static ArchiveFeedDTO of(CarArchiving carArchiving, Map<String, List<String>> carArchiveResult,
		CarReview carReview, List<TagResponseDTO> tags) {
		return ArchiveFeedDTO.builder()
			.archivingId(carArchiving.getArchivingId())
			.carArchiveResult(carArchiveResult)
			.dayTime(carArchiving.getDayTime())
			.carReview(carReview == null ? null : carReview.getReview())
			.tags(tags)
			.type(carArchiving.getArchivingType())
			.build();
	}
}
