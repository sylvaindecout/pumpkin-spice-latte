import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { CatalogueItem } from './catalogue-item';

@Injectable({
  providedIn: 'root'
})
export class CatalogueService {

  constructor(private readonly http: HttpClient) {
  }

  findAllDrinks(): Observable<CatalogueItem[]> {
    return this.http.get<CatalogueItem[]>('/api/catalogue/drinks/');
  }

}
