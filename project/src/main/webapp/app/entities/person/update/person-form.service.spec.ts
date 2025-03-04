import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../person.test-samples';

import { PersonFormService } from './person-form.service';

describe('Person Form Service', () => {
  let service: PersonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonFormService);
  });

  describe('Service methods', () => {
    describe('createPersonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameAndFirstName: expect.any(Object),
            lastEvent: expect.any(Object),
            lastEventDate: expect.any(Object),
            phoneNumber: expect.any(Object),
            email: expect.any(Object),
            eventCount: expect.any(Object),
            ticketCount: expect.any(Object),
            firstEventDate: expect.any(Object),
            soldForTheAmount: expect.any(Object),
            allEvents: expect.any(Object),
            mailings: expect.any(Object),
            regionResidence: expect.any(Object),
          }),
        );
      });

      it('passing IPerson should create a new form with FormGroup', () => {
        const formGroup = service.createPersonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameAndFirstName: expect.any(Object),
            lastEvent: expect.any(Object),
            lastEventDate: expect.any(Object),
            phoneNumber: expect.any(Object),
            email: expect.any(Object),
            eventCount: expect.any(Object),
            ticketCount: expect.any(Object),
            firstEventDate: expect.any(Object),
            soldForTheAmount: expect.any(Object),
            allEvents: expect.any(Object),
            mailings: expect.any(Object),
            regionResidence: expect.any(Object),
          }),
        );
      });
    });

    describe('getPerson', () => {
      it('should return NewPerson for default Person initial value', () => {
        const formGroup = service.createPersonFormGroup(sampleWithNewData);

        const person = service.getPerson(formGroup) as any;

        expect(person).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerson for empty Person initial value', () => {
        const formGroup = service.createPersonFormGroup();

        const person = service.getPerson(formGroup) as any;

        expect(person).toMatchObject({});
      });

      it('should return IPerson', () => {
        const formGroup = service.createPersonFormGroup(sampleWithRequiredData);

        const person = service.getPerson(formGroup) as any;

        expect(person).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerson should not enable id FormControl', () => {
        const formGroup = service.createPersonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerson should disable id FormControl', () => {
        const formGroup = service.createPersonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
