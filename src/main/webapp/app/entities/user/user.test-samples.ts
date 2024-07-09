import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 17484,
  login: '5@yoI\\$qep',
};

export const sampleWithPartialData: IUser = {
  id: 6911,
  login: '=q@Gz\\=4CmD3\\TSh5ZIY',
};

export const sampleWithFullData: IUser = {
  id: 1845,
  login: 'P!!gp@hywp2j\\/qktf',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
