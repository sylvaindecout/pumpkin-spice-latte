import {NgModule} from '@angular/core';

import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {PreparationListComponent} from './preparation-list/preparation-list.component';
import {PreparationEffects} from './state/preparation.effects';
import {preparationReducer} from './state/preparation.reducer';

@NgModule({
  imports: [
    StoreModule.forFeature('preparation', preparationReducer),
    EffectsModule.forFeature([PreparationEffects]),

    PreparationListComponent,
  ],
  exports: [
    PreparationListComponent,
  ],
})
export class PreparationModule {
}
