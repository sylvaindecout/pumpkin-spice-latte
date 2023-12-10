import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { AppComponent } from './app.component';
import { OrderingModule } from './ordering/ordering.module';
import { SharedModule } from './shared/shared.module';
import { PreparationModule } from './preparation/preparation.module';
import { menuReducer } from './menu/state/menu.reducer';
import { MenuEffects } from './menu/state/menu.effects';
import {NgOptimizedImage} from "@angular/common";

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
    NgOptimizedImage,

    StoreModule.forRoot({
      menu: menuReducer,
    }),
    EffectsModule.forRoot([
      MenuEffects
    ]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
