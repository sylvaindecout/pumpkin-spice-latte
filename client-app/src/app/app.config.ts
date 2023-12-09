import { ApplicationConfig } from '@angular/core';

import { importProvidersFrom } from '@angular/core';
import { MenuEffects } from './menu/state/menu.effects';
import { EffectsModule } from '@ngrx/effects';
import { menuReducer } from './menu/state/menu.reducer';
import { StoreModule } from '@ngrx/store';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { PreparationModule } from './preparation/preparation.module';
import { OrderingModule } from './ordering/ordering.module';

export const appConfig: ApplicationConfig = {
  providers: [
    importProvidersFrom(
      OrderingModule,
      PreparationModule,
      BrowserModule,
      StoreModule.forRoot({ menu: menuReducer }),
      EffectsModule.forRoot([MenuEffects])
    ),
    provideAnimations(),
    provideHttpClient(),
  ]
};
