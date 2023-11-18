import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { AppComponent } from './app.component';
import { OrderingModule } from './ordering/ordering.module';
import { SharedModule } from './shared/shared.module';
import { PreparationModule } from './preparation/preparation.module';
import { catalogueReducer } from './catalogue/state/catalogue.reducer';
import { CatalogueEffects } from './catalogue/state/catalogue.effects';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    OrderingModule,
    PreparationModule,
    SharedModule,

    BrowserModule,
    BrowserAnimationsModule,

    StoreModule.forRoot({
      catalogue: catalogueReducer,
    }),
    EffectsModule.forRoot([
      CatalogueEffects
    ]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
