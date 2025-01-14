import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { engineInfoInterface } from '@pages/MyCar/Trim/Engine';
import { theme } from '@styles/theme';

export const EngineCard = ({
  engine,
  isSelected,
}: {
  engine: engineInfoInterface;
  isSelected: boolean;
}) => {
  return (
    <EngineBox
      direction="column"
      padding="28px 23px"
      gap={22}
      isSelected={isSelected === true}
    >
      <Flex justify="space-between">
        <Text typo="Heading3_Bold">{engine.optionName}</Text>
        <Text typo="Heading4_Bold">+{engine.price.toLocaleString()}원</Text>
      </Flex>
      <Flex>
        <Text typo="Body3_Regular">{engine.optionDetail}</Text>
      </Flex>
      <BorderLine />
      <Flex direction="column" gap={8}>
        <Flex justify="space-between" height={18}>
          <Text typo="Body3_Medium">최고출력</Text>
          <Text typo="Body3_Regular">{engine.maxOutput}</Text>
        </Flex>
        <Flex justify="space-between" height={18}>
          <Text typo="Body3_Medium">최대토크</Text>
          <Text typo="Body3_Regular">{engine.maxTorque}</Text>
        </Flex>
      </Flex>
    </EngineBox>
  );
};

const EngineBox = styled(Flex)<{ isSelected: boolean }>`
  width: 391px;
  background-color: ${({ isSelected }) =>
    isSelected ? 'rgba(0, 44, 95, 0.10)' : theme.palette.LightSand};
  border: ${({ isSelected }) =>
    isSelected
      ? `2px solid ${theme.palette.Primary}`
      : `2px solid ${theme.palette.White}`};
  color: ${({ isSelected }) =>
    isSelected ? theme.palette.Primary : theme.palette.Black};
  border-radius: 8px;
  cursor: pointer;

  &:hover {
    border: 2px solid ${theme.palette.Primary};
  }
  transition: ease 0.3s;
`;

const BorderLine = styled.div`
  width: 345.402px;
  height: 0px;
  flex-shrink: 0;

  border: 1px solid ${theme.palette.Primary};
`;
