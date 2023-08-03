import { Flex } from '../../components/common/Flex';
import Footer from '../../components/myCar/Footer';
import NavBar from '../../components/myCar/NavBar';
import Header from '../../components/myCar/Header';
import Trim from './Trim';
import { useState } from 'react';

export interface footerInterface {
  name: string;
  color: string[];
  option: { outer: string; inner: string };
  price: number;
}

const MyCar = () => {
  const [footerInfo, setFooterInfo] = useState({
    name: 'Le Blanc',
    color: [],
    option: { outer: '', inner: '' },
    price: 47720000,
  } as footerInterface);

  return (
    <Flex direction="column" align="center">
      <Header />
      <NavBar />
      <Flex direction="column" width={1040}>
        <Trim footerInfo={footerInfo} setFooterInfo={setFooterInfo} />
      </Flex>
      <Footer footerInfo={footerInfo} />
    </Flex>
  );
};

export default MyCar;
