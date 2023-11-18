import { NgModule } from '@angular/core';

import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { SharedModule } from '../shared/shared.module';
import { PreparationListComponent } from './preparation-list/preparation-list.component';
import { PreparationEffects } from './state/preparation.effects';
import { preparationReducer } from './state/preparation.reducer';

@NgModule({
  declarations: [
    PreparationListComponent,
  ],
  imports: [
    SharedModule,

    StoreModule.forFeature('preparation', preparationReducer),
    EffectsModule.forFeature([PreparationEffects]),
  ],
  exports: [
    PreparationListComponent,
  ],
})
export class PreparationModule { }
