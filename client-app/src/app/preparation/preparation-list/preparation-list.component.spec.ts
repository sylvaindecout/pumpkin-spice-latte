import {TestBed} from '@angular/core/testing';
import {provideMockStore} from "@ngrx/store/testing";
import {initialState} from '../state/preparation.reducer';
import {PreparationListComponent} from './preparation-list.component';

describe('PreparationListComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [PreparationListComponent],
    providers: [
      provideMockStore({initialState})
    ],
  }));

  it('should create the component', () => {
    const fixture = TestBed.createComponent(PreparationListComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
