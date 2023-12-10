import {TestBed} from '@angular/core/testing';
import {MatDialogRef} from '@angular/material/dialog';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatStepperModule} from "@angular/material/stepper";
import {provideMockStore} from '@ngrx/store/testing';
import {initialState} from '../state/invoices.reducer';
import {NewOrderDialogComponent} from './new-order-dialog.component';

describe('NewOrderDialogComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      MatFormFieldModule,
      MatStepperModule,
      NewOrderDialogComponent,
    ],
    providers: [
      {provide: MatDialogRef, useValue: {}},
      provideMockStore({initialState}),
    ],
  }));

  it('should create the component', () => {
    const fixture = TestBed.createComponent(NewOrderDialogComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
