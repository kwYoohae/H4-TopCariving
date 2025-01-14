package com.backend.topcariving.domain.repository.option;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.dto.archive.response.PositionDTO;
import com.backend.topcariving.domain.entity.option.Position;
import com.backend.topcariving.domain.repository.option.implement.PositionRepositoryImpl;

@JdbcTest
class PositionRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private PositionRepositoryImpl positionRepository;

	@BeforeEach
	void setup() {
		positionRepository = new PositionRepositoryImpl(jdbcTemplate);
	}

	@Nested
	@DisplayName("차량 옵션 아이디로 포지션 조회")
	class FindByCarOptionId {
		@Test
		void 포지션이_없는_차량_옵션_아이디일_때_empty를_반환해야한다() {
			// given
			Long carOptionId = 103L;

			// when
			Optional<Position> position = positionRepository.findByCarOptionId(carOptionId);

			// then
			Assertions.assertThat(position).isEqualTo(Optional.empty());
		}

		@Test
		void 포지션이_있는_차량_옵션_아이디일_때_포지션을_반환해야한다() {
			// given
			Long carOptionId = 120L;

			// when
			Optional<Position> position = positionRepository.findByCarOptionId(carOptionId);

			// then
			Position findPosition = position.get();
			Assertions.assertThat(findPosition.getPositionId()).isEqualTo(5L);
		}
	}

	@Test
	void findPositionDTOByCarOptionIds() {
		// given
		List<Long> carOptionIds = List.of(120L, 122L);

		// when
		List<PositionDTO> positions = positionRepository.findPositionDTOByCarOptionIds(carOptionIds);

		// then
		PositionDTO position0 = positions.get(0);
		softAssertions.assertThat(position0.getPositionId()).isEqualTo(5L);
		softAssertions.assertThat(position0.getOptionName()).isEqualTo("듀얼 머플러 패키지");
		PositionDTO position1 = positions.get(1);
		softAssertions.assertThat(position1.getPositionId()).isEqualTo(7L);
		softAssertions.assertThat(position1.getOptionName()).isEqualTo("사이드스텝");
	}

}