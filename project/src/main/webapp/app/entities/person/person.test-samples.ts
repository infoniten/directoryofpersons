import dayjs from 'dayjs/esm';

import { IPerson, NewPerson } from './person.model';

export const sampleWithRequiredData: IPerson = {
  id: 17743,
};

export const sampleWithPartialData: IPerson = {
  id: 16297,
  nameAndFirstName: 'yearningly',
  lastEvent: 'for verbally',
  lastEventDate: dayjs('2024-07-09'),
};

export const sampleWithFullData: IPerson = {
  id: 27887,
  nameAndFirstName: 'grandiose beneath owlishly',
  lastEvent: 'latency now past',
  lastEventDate: dayjs('2024-07-08'),
  phoneNumber: 'glamorous than while',
  email: 'Milovan.Nitzsche@yahoo.com',
  eventCount: 27406,
  ticketCount: 3514,
  firstEventDate: dayjs('2024-07-09'),
  soldForTheAmount: 27466.82,
  allEvents: 'brake',
  mailings: 'fortnight incidentally',
  regionResidence: 'before extremely when',
};

export const sampleWithNewData: NewPerson = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
