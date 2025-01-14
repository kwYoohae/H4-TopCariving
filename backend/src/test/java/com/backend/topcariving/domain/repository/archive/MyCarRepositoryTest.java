package com.backend.topcariving.domain.repository.archive;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.entity.archive.MyCar;
import com.backend.topcariving.domain.dto.option.response.estimation.OptionSummaryDTO;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.repository.archive.implement.MyCarRepositoryImpl;

@JdbcTest
class MyCarRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private MyCarRepositoryImpl myCarRepository;

	@BeforeEach
	void setUp() {
		myCarRepository = new MyCarRepositoryImpl(jdbcTemplate);
	}

	@Test
	void save() {
		// given
		MyCar myCar = MyCar.builder()
			.archivingId(1L)
			.carOptionId(3L)
			.build();

		// when
		myCar = myCarRepository.save(myCar);

		// then
		final MyCar findMyCar = myCarRepository.findById(myCar.getMyCarId()).get();
		Assertions.assertThat(myCar).usingRecursiveComparison().isEqualTo(findMyCar);
	}

	@Test
	void findByArchivingIdAndCarOptionId() {
		// given
		Long archivingId = 1L;
		Long carOptionId = 1L;

		// when
		final MyCar myCar = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId).get();

		// then
		softAssertions.assertThat(myCar.getCarOptionId()).as("옵션 ID 검증").isEqualTo(carOptionId);
		softAssertions.assertThat(myCar.getMyCarId()).as("마이 카 ID 검증").isEqualTo(1L);
		softAssertions.assertThat(myCar.getArchivingId()).as("아카이빙 ID 검증").isEqualTo(archivingId);
	}

	@Test
	void findByArchivingId() {
		// given
		Long archivingId = 1L;

		// when
		final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);

		// then
		Assertions.assertThat(myCars).hasSize(9);
	}

	@Test
	void findOptionSummaryByArchivingId() {
		// given
		Long archivingId = 1L;

		// when
		final List<OptionSummaryDTO> OptionSummaryDTOs = myCarRepository.findOptionSummaryByArchivingId(
			archivingId);

		// then
		final OptionSummaryDTO optionSummary = OptionSummaryDTOs.stream()
			.filter(optionSummaryDTO -> optionSummaryDTO.getCarOptionId() == 1L)
			.findFirst()
			.orElse(null);
		softAssertions.assertThat(optionSummary.getCategory()).as("카테고리 테스트").isEqualTo(Category.TRIM);
		softAssertions.assertThat(optionSummary.getCategoryDetail()).as("카테고리 세부정보 테스트").isEqualTo(CategoryDetail.MODEL);
		softAssertions.assertThat(optionSummary.getName()).as("이름 테스트").isEqualTo("Le Blanc");

	}

	@Test
	void deleteByArchivingIdAndCategoryDetail() {
		// given
		Long archivingId = 1L;
		CategoryDetail categoryDetail = CategoryDetail.MODEL;

		// when
		myCarRepository.deleteByArchivingIdAndCategoryDetail(archivingId, categoryDetail);

		// then
		final Optional<MyCar> findMyCar = myCarRepository.findById(1L);
		Assertions.assertThat(findMyCar).isEmpty();
	}

	@Test
	void updateCarOptionIdByArchivingIdAndCategoryDetail() {
		// given
		Long archivingId = 1L;
		Long carOptionId = 2L;
		
		// when
		myCarRepository.updateCarOptionIdByArchivingIdAndCategoryDetail(archivingId, carOptionId, CategoryDetail.MODEL);
		
		// then
		final MyCar findMyCar = myCarRepository.findById(1L).get();
		softAssertions.assertThat(findMyCar.getCarOptionId()).as("차 옵션 ID 테스트").isEqualTo(2L);
		softAssertions.assertThat(findMyCar.getArchivingId()).as("아카이빙 ID 테스트").isEqualTo(1L);

	}
}