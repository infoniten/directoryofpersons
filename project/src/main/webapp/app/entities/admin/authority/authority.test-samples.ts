import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'cb5249f1-03df-4fdc-aed8-6d9e86b03003',
};

export const sampleWithPartialData: IAuthority = {
  name: 'bbb32e25-2930-47b6-9768-fda67c642c42',
};

export const sampleWithFullData: IAuthority = {
  name: '4a2ada18-08e7-4388-a38c-6473ebf34f3c',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
