import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import leftBtn from '@assets/images/leftBtn.svg';
import rightBtn from '@assets/images/rightBtn.svg';
import { optionInfoInterface } from '@interface/index';

export const OptionInfoCard = ({
  info,
  onMovePage,
  cardPageIdx,
}: {
  info: optionInfoInterface[];
  onMovePage: ({ pageParam }: { pageParam: number }) => void;
  cardPageIdx: number;
}) => {
  const total = info.length;

  return (
    <InfoContainer>
      <InfoTitleContainer>
        <Flex justify="flex-start" gap={8}>
          <Flex
            backgroundColor="Primary"
            borderRadius="50%"
            width={22}
            height={22}
            padding="6px"
          >
            <Text palette="LightSand">{cardPageIdx + 1}</Text>
          </Flex>
          <Text palette="Primary">{info[cardPageIdx].optionName}</Text>
        </Flex>
        <Flex
          justify="flex-end"
          backgroundColor="DarkGray"
          borderRadius="13px"
          width="auto"
          padding="0 9px"
        >
          <Text palette="LightSand" typo="Caption_Regular">
            {cardPageIdx + 1}/{total}
          </Text>
        </Flex>
      </InfoTitleContainer>
      <Flex justify="space-between">
        <ButtonContainer onClick={() => onMovePage({ pageParam: -1 })}>
          <img src={leftBtn} alt="" />
        </ButtonContainer>
        <Text typo="Body3_Regular" palette="Primary">
          {info[cardPageIdx].optionDetail}
        </Text>
        <ButtonContainer onClick={() => onMovePage({ pageParam: +1 })}>
          <img src={rightBtn} alt="" />
        </ButtonContainer>
      </Flex>
    </InfoContainer>
  );
};

const ButtonContainer = styled.div`
  cursor: pointer;
`;

const InfoTitleContainer = styled(Flex)`
  justify-content: space-between;
  border-bottom: 2px solid ${theme.palette.Primary};

  width: 443px;
  height: auto;

  padding: 20px 0 15px 0;
`;

const InfoContainer = styled(Flex)`
  flex-direction: column;
  width: 507px;
  height: 152px;

  border: 2px solid ${theme.palette.Primary};
  border-radius: 8px;

  background-color: rgba(0, 44, 95, 0.1);
`;
