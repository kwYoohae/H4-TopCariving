package com.backend.topcariving.domain.repository.archive;

import static com.backend.topcariving.domain.entity.archive.enums.ArchivingType.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.dto.projection.CarDTO;
import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;
import com.backend.topcariving.domain.entity.archive.CarArchiving;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.repository.archive.implement.CarArchivingRepositoryImpl;

@JdbcTest
class CarArchivingRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private CarArchivingRepositoryImpl carArchivingRepository;

	@BeforeEach
	void setUp() {
		carArchivingRepository = new CarArchivingRepositoryImpl(jdbcTemplate);
	}

	@Test
	void save() {
		// given
		CarArchiving carArchiving = CarArchiving.builder()
			.archivingType(ArchivingType.MAKE)
			.isComplete(false)
			.userId(1L)
			.isAlive(false)
			.build();

		// when
		carArchiving = carArchivingRepository.save(carArchiving);

		// then
		final CarArchiving findCarArchiving = carArchivingRepository.findById(carArchiving.getArchivingId())
			.get();
		softAssertions.assertThat(carArchiving.getArchivingId()).as("아카이빙 아이디 검증").isEqualTo(findCarArchiving.getArchivingId());
		softAssertions.assertThat(carArchiving.getUserId()).as("사용자 아이디 검증").isEqualTo(findCarArchiving.getUserId());
		softAssertions.assertThat(carArchiving.getIsAlive()).as("저장 여부 검증").isEqualTo(findCarArchiving.getIsAlive());
		softAssertions.assertThat(carArchiving.getIsComplete()).as("만들기 완료 여부 검증").isEqualTo(findCarArchiving.getIsComplete());
		softAssertions.assertThat(carArchiving.getArchivingType()).as("아카이빙 타입 검증").isEqualTo(findCarArchiving.getArchivingType());
	}

	@Test
	void existsByUserIdAndArchivingId() {
		// given
		Long userId = 1L;
		Long archivingId = 1L;

		// when
		final boolean isExists = carArchivingRepository.existsByUserIdAndArchivingId(userId, archivingId);

		// then
		Assertions.assertThat(isExists).isTrue();
	}

	@Test
	void existsByArchivingId() {
		// given
		Long archivingIdTrue = 1L;
		Long archivingIdFalse = 21L;

		// when
		boolean isExistsTrue = carArchivingRepository.existsByArchivingId(archivingIdTrue);
		boolean isExistsFalse = carArchivingRepository.existsByArchivingId(archivingIdFalse);

		// then
		softAssertions.assertThat(isExistsTrue).as("1번 차량이 존재해야 함").isTrue();
		softAssertions.assertThat(isExistsFalse).as("21번 차량이 존재하지 않아야 함").isFalse();
	}

	@Test
	void updateIsCompleteByArchivingId() {
		// given
		CarArchiving carArchiving = CarArchiving.builder()
			.archivingType(ArchivingType.MAKE)
			.isComplete(false)
			.userId(1L)
			.isAlive(false)
			.build();
		carArchiving = carArchivingRepository.save(carArchiving);

		// when
		carArchivingRepository.updateIsCompleteByArchivingId(carArchiving.getArchivingId(), true);

		// then
		final CarArchiving findCarArchiving = carArchivingRepository.findById(carArchiving.getArchivingId()).get();
		Assertions.assertThat(findCarArchiving.getIsComplete()).isTrue();
	}

	@Test
	void updateIsAliveByArchivingId() {
		// given
		CarArchiving carArchiving = CarArchiving.builder()
			.archivingType(ArchivingType.MAKE)
			.isComplete(true)
			.userId(1L)
			.isAlive(true)
			.build();
		carArchiving = carArchivingRepository.save(carArchiving);

		// when
		carArchivingRepository.updateIsAliveByArchivingId(false, carArchiving.getArchivingId());

		// then
		CarArchiving findCarArchiving = carArchivingRepository.findById(carArchiving.getArchivingId()).get();
		Assertions.assertThat(findCarArchiving.getIsAlive()).isFalse();
	}

	@Test
	void findByNotExistId() {
		// given
		Long archivingId = 100L;

		// when
		Optional<CarArchiving> carArchiving = carArchivingRepository.findById(archivingId);

		// then
		Assertions.assertThat(carArchiving).isEmpty();
	}

	@Test
	void findByCarOptionIdsAndArchivingTypes() {
		// given, when
		List<CarArchiving> carArchivings = carArchivingRepository.findByCarOptionIdsAndArchivingTypes(
			List.of(103L, 110L), List.of(DRIVE, BUY), 1, 3
		);

		// then
		softAssertions.assertThat(carArchivings.get(0).getArchivingId()).as("첫 번째 아카이빙의 아카이빙 아이디 검증").isEqualTo(3L);
		softAssertions.assertThat(carArchivings.get(0).getArchivingType()).as("첫 번째 아카이빙의 아카이빙 타입 검증").isEqualTo(DRIVE);
		softAssertions.assertThat(carArchivings.get(1).getArchivingId()).as("두 번째 아카이빙의 아카이빙 아이디 검증").isEqualTo(1L);
		softAssertions.assertThat(carArchivings.get(1).getArchivingType()).as("두 번째 아카이빙의 아카이빙 타입 검증").isEqualTo(DRIVE);
	}

	@Test
	void findByArchivingTypes() {
		// given, when
		List<ArchivingType> types = List.of(DRIVE, BUY);
		List<CarArchiving> carArchivings = carArchivingRepository.findByArchivingTypes(types, 1, 3);

		// then
		softAssertions.assertThat(carArchivings.get(0).getArchivingId()).as("3이 반환되어야 함").isEqualTo(3L);
		softAssertions.assertThat(carArchivings.get(0).getArchivingType()).as("'시승'이 반환되어야 함'").isEqualTo(DRIVE);
		softAssertions.assertThat(carArchivings.get(1).getArchivingId()).as("2가 반환되어야 함").isEqualTo(2L);
		softAssertions.assertThat(carArchivings.get(1).getArchivingType()).as("'구매'가 반환되어야 함'").isEqualTo(BUY);
		softAssertions.assertThat(carArchivings.get(2).getArchivingId()).as("1이 반환되어야 함").isEqualTo(1L);
		softAssertions.assertThat(carArchivings.get(2).getArchivingType()).as("'시승'이 반환되어야 함'").isEqualTo(DRIVE);
	}

	@Test
	void findCarDTOByUserIdAndOffsetAndPageSize() {
		// given
		Long userId = 1L;
		Integer pageNumber = 1;
		Integer pageSize = 1;

		// when
		List<CarDTO> cars = carArchivingRepository.findCarDTOByUserIdAndOffsetAndPageSize(
			userId, pageNumber, pageSize);

		// then
		softAssertions.assertThat(cars).as("pageSize가 1이므로 한개가 나와야한다").hasSize(1);

		CarDTO car = cars.get(0);
		softAssertions.assertThat(car.getArchivingType()).as("아카이빙 타입 테스트").isEqualTo(DRIVE);
		softAssertions.assertThat(car.getArchivingId()).as("아카이빙 아이디 테스트").isEqualTo(1L);
		softAssertions.assertThat(car.getCarOptions()).as("옵션을 몇개 가지고 있는지 테스트").hasSize(8);
		List<CarOption> carOptions = car.getCarOptions();
		CarOption carOption = carOptions.get(0);
		softAssertions.assertThat(carOption.getCarOptionId()).as("고른 옵션이 르블랑인지 테스트").isEqualTo(1L);
	}

	@Test
	void findCarDTOByUserIdAndOffsetAndPageSizeNoResult() {
		// given
		Long userId = 1L;
		Integer pageNumber = -1;
		Integer pageSize = 0;

		// when
		List<CarDTO> cars = carArchivingRepository.findCarDTOByUserIdAndOffsetAndPageSize(userId, pageNumber, pageSize);

		// then
		Assertions.assertThat(cars).hasSize(0);
	}

	@Test
	void findCarDTOByUserIdAndOffsetAndPageSizeAndAliveTrueNoResult() {
		// given
		Long userId = 1L;
		Integer pageNumber = -1;
		Integer pageSize = 0;

		// when
		List<CarDTO> cars = carArchivingRepository.findCarDTOByUserIdAndOffsetAndPageSizeAndAliveTrue(userId, pageNumber, pageSize);

		// then
		Assertions.assertThat(cars).hasSize(0);
	}
}