import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrinkInPreparation } from './drink-in-preparation';
import { PreparationModule } from './preparation.module';

@Injectable({
  providedIn: 'root'
})
export class PreparationService {

  constructor(private readonly http: HttpClient) {
  }

  findAll(): Observable<DrinkInPreparation[]> {
    return this.http.get<DrinkInPreparation[]>('/api/preparation/drinks');
  }

}
