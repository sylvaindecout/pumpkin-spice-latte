import {NgModule} from '@angular/core';

import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatOptionModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatStepperModule} from '@angular/material/stepper';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {InvoiceListComponent} from './invoice-list/invoice-list.component';
import {NewOrderDialogComponent} from './new-order-dialog/new-order-dialog.component';
import {OrderingComponent} from './ordering/ordering.component';
import {InvoicesEffects} from './state/invoices.effects';
import {invoicesReducer} from './state/invoices.reducer';

@NgModule({
  imports: [
    StoreModule.forFeature('invoices', invoicesReducer),
    EffectsModule.forFeature([InvoicesEffects]),

    MatButtonModule,
    MatCheckboxModule,
    MatDialogModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatOptionModule,
    MatRadioModule,
    MatSelectModule,
    MatSnackBarModule,
    MatStepperModule,

    InvoiceListComponent,
    OrderingComponent,
    NewOrderDialogComponent,
  ],
  exports: [
    OrderingComponent,
  ],
})
export class OrderingModule {
}
