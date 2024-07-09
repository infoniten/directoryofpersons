import dayjs from 'dayjs/esm';

export interface IPerson {
  id: number;
  nameAndFirstName?: string | null;
  lastEvent?: string | null;
  lastEventDate?: dayjs.Dayjs | null;
  phoneNumber?: string | null;
  email?: string | null;
  eventCount?: number | null;
  ticketCount?: number | null;
  firstEventDate?: dayjs.Dayjs | null;
  soldForTheAmount?: number | null;
  allEvents?: string | null;
  mailings?: string | null;
  regionResidence?: string | null;
}

export type NewPerson = Omit<IPerson, 'id'> & { id: null };
