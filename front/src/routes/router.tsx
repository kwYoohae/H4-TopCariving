import MyCar from '@pages/MyCar';
import BodyType from '@pages/MyCar/Trim/BodyType';
import { createBrowserRouter } from 'react-router-dom';
import Engine, { engineInfoInterface } from '@pages/MyCar/Trim/Engine';
import { Trim } from '@pages/MyCar/Trim';
import Traction from '@pages/MyCar/Trim/Traction';
import { MyCarOptions } from '@pages/MyCar/Option';
import Login from '@pages/Login';
import Error from '@pages/Error';
import Color from '@pages/MyCar/Color';
import Complete from '@pages/MyCar/Complete';

import {
  OptionUrl,
  TrimUrl,
  apiInstance,
  Summary,
  ArchiveUrl,
} from '@utils/api';
import { TrimCardInterface } from '@components/makeMyCar/trim';
import { myCarOptionInterface } from '@interface/index';
import { ArchiveDetail } from '@pages/Archive/detail';
import { Archive } from '@pages/Archive';
import { ArchiveMain, archiveMainInterface } from '@pages/Archive/main';
import { MyCariving } from '@pages/Archive/mycariving';
import { getArchivingId } from '@components/makeMyCar';
import { OauthRedirect } from '@pages/Login/oauthRedirect';

export const router = createBrowserRouter([
  { path: '/', element: <Login /> },
  { path: '/*', element: <Error /> },
  { path: '/oauth-redirect', element: <OauthRedirect /> },
  {
    path: '/my-car',
    element: <MyCar />,
    children: [
      { path: '', element: <></> },
      {
        path: 'trim',
        element: <Trim />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.MODELS,
            method: 'GET',
          })) as TrimCardInterface[];
        },
      },
      {
        path: 'trim/engine',
        element: <Engine />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.ENGINES,
            method: 'GET',
          })) as engineInfoInterface[];
        },
      },
      {
        path: 'trim/body-type',
        element: <BodyType />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.BODY_TYPE,
            method: 'GET',
          })) as myCarOptionInterface[];
        },
      },
      {
        path: 'trim/traction',
        element: <Traction />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.TRACTION,
            method: 'GET',
          })) as myCarOptionInterface[];
        },
      },
      {
        path: 'color',
        element: <Color />,
      },
      {
        path: 'option',
        element: <MyCarOptions key={'option'} />,
        loader: async () => {
          const [selectOptionData, defaultData] = await Promise.all([
            await apiInstance({
              url: OptionUrl.SELECTION,
              method: 'GET',
            }),
            await apiInstance({
              url: OptionUrl.BASIC,
              method: 'GET',
            }),
          ]);

          return {
            selectOptionData: selectOptionData,
            defaultOptionData: defaultData.data,
          };
        },
      },
      {
        path: 'option/genuine',
        element: <MyCarOptions key={'genuine'} />,
        loader: async () => {
          const [selectOptionData, defaultData] = await Promise.all([
            await apiInstance({
              url: OptionUrl.ACCESSORY,
              method: 'GET',
            }),
            await apiInstance({
              url: OptionUrl.BASIC,
              method: 'GET',
            }),
          ]);

          return {
            selectOptionData: selectOptionData,
            defaultOptionData: defaultData.data,
          };
        },
      },
      {
        path: 'option/performance',
        element: <MyCarOptions key={'performance'} />,
        loader: async () => {
          const [selectOptionData, defaultData] = await Promise.all([
            await apiInstance({
              url: OptionUrl.PERFORMANCE,
              method: 'GET',
            }),
            await apiInstance({
              url: OptionUrl.BASIC,
              method: 'GET',
            }),
          ]);

          return {
            selectOptionData: selectOptionData,
            defaultOptionData: defaultData.data,
          };
        },
      },
    ],
  },
  {
    path: '/my-car/complete',
    element: <Complete />,
    loader: async () => {
      const { options } = await apiInstance({
        url: `${Summary.ESTIMATION}/${getArchivingId()}`,
        method: 'GET',
      });
      return options;
    },
  },
  {
    path: '/my-archive',
    element: <MyCariving />,
  },
  {
    path: '/archive',
    element: <Archive />,
    children: [
      {
        path: '',
        element: <ArchiveMain />,
      },
      {
        path: 'detail',
        element: <ArchiveDetail />,
      },
    ],
  },
]);
