import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {Money} from '../shared/money';
import {Invoice} from './invoice';
import {Order} from './order';

@Injectable({
  providedIn: 'root'
})
export class OrderingService {

  constructor(private readonly http: HttpClient) {
  }

  process(order: Order): Observable<Invoice> {
    return this.http.post<OrderResponse>('/api/orders', order).pipe(
      map(orderResponse => ({...order, ...orderResponse}))
    );
  }

}

interface OrderResponse {
  totalPrice: Money,
}
