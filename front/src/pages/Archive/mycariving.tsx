import { ArchiveHeader, ArchiveNavbar } from '@components/archive';
import { MyCarList } from '@components/archive/myCariving/MyCarList';
import { Flex } from '@components/common';
import { TopMargin } from '.';

export const MyCariving = () => {
  return (
    <Flex direction="column" justify="flex-start">
      <ArchiveHeader pageTitle={'마이카이빙'} />
      <TopMargin />
      <ArchiveNavbar pageTitle={'마이카이빙'} />
      <MyCarList />
    </Flex>
  );
};