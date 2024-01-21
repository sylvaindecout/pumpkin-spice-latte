import {Money} from '../shared/money';

export interface Invoice {
  drink: string,
  quantity: number,
  customer: string,
  totalPrice: Money,
}
