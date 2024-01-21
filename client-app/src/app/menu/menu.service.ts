import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {MenuItem} from './menu-item';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private readonly http: HttpClient) {
  }

  findAllDrinks(): Observable<MenuItem[]> {
    return this.http.get<MenuItem[]>('/api/menu/drinks/');
  }

}
