import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPerson, NewPerson } from '../person.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerson for edit and NewPersonFormGroupInput for create.
 */
type PersonFormGroupInput = IPerson | PartialWithRequiredKeyOf<NewPerson>;

type PersonFormDefaults = Pick<NewPerson, 'id'>;

type PersonFormGroupContent = {
  id: FormControl<IPerson['id'] | NewPerson['id']>;
  nameAndFirstName: FormControl<IPerson['nameAndFirstName']>;
  lastEvent: FormControl<IPerson['lastEvent']>;
  lastEventDate: FormControl<IPerson['lastEventDate']>;
  phoneNumber: FormControl<IPerson['phoneNumber']>;
  email: FormControl<IPerson['email']>;
  eventCount: FormControl<IPerson['eventCount']>;
  ticketCount: FormControl<IPerson['ticketCount']>;
  firstEventDate: FormControl<IPerson['firstEventDate']>;
  soldForTheAmount: FormControl<IPerson['soldForTheAmount']>;
  allEvents: FormControl<IPerson['allEvents']>;
  mailings: FormControl<IPerson['mailings']>;
  regionResidence: FormControl<IPerson['regionResidence']>;
};

export type PersonFormGroup = FormGroup<PersonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonFormService {
  createPersonFormGroup(person: PersonFormGroupInput = { id: null }): PersonFormGroup {
    const personRawValue = {
      ...this.getFormDefaults(),
      ...person,
    };
    return new FormGroup<PersonFormGroupContent>({
      id: new FormControl(
        { value: personRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nameAndFirstName: new FormControl(personRawValue.nameAndFirstName),
      lastEvent: new FormControl(personRawValue.lastEvent),
      lastEventDate: new FormControl(personRawValue.lastEventDate),
      phoneNumber: new FormControl(personRawValue.phoneNumber),
      email: new FormControl(personRawValue.email),
      eventCount: new FormControl(personRawValue.eventCount),
      ticketCount: new FormControl(personRawValue.ticketCount),
      firstEventDate: new FormControl(personRawValue.firstEventDate),
      soldForTheAmount: new FormControl(personRawValue.soldForTheAmount),
      allEvents: new FormControl(personRawValue.allEvents),
      mailings: new FormControl(personRawValue.mailings),
      regionResidence: new FormControl(personRawValue.regionResidence),
    });
  }

  getPerson(form: PersonFormGroup): IPerson | NewPerson {
    return form.getRawValue() as IPerson | NewPerson;
  }

  resetForm(form: PersonFormGroup, person: PersonFormGroupInput): void {
    const personRawValue = { ...this.getFormDefaults(), ...person };
    form.reset(
      {
        ...personRawValue,
        id: { value: personRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PersonFormDefaults {
    return {
      id: null,
    };
  }
}
