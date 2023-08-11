import { Button, Flex, Text } from '@components/common';
import { useMyCar } from '@contexts/MyCarContext';
import { css } from '@emotion/react';

export const Footer = () => {
  const { myCarInfo } = useMyCar();
  return (
    <Flex
      backgroundColor="Sand"
      height={108}
      borderRadius="16px 16px"
      padding="12px 36px 24px 36px"
      justify="flex-end"
      gap={24}
      css={css`
        position: fixed;
        bottom: 0px;
        flex-shrink: 0;
      `}
    >
      <Flex width="auto" gap={4} margin="18px 0 0 0">
        <Text typo="Body3_Medium" palette="DarkGray">
          예상 견적 가격
        </Text>
        <Text typo="Heading1_Bold">
          {myCarInfo.price.toLocaleString('ko-KR')}
        </Text>
        <Text typo="Body3_Medium">원</Text>
      </Flex>
      <Button backgroundColor="Primary" heightType="medium" width={176}>
        <Text palette="White" typo="Heading4_Bold">
          이 차량 구매하기
        </Text>
      </Button>
    </Flex>
  );
};
