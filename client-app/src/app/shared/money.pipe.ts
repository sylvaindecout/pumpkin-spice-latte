import { formatCurrency, getCurrencySymbol } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';
import { Money } from './money';

@Pipe({
  name: 'money',
  standalone: true,
})
export class MoneyPipe implements PipeTransform {

  transform(money: Money): string {
    const value = money.amountMinor / Math.pow(10, money.scale);
    const currencyCode = money.currencyUnit;
    const locale: string = 'en';
    const digitsInfo = `1.${money.scale}-${money.scale}`;
    return formatCurrency(
      value,
      locale,
      getCurrencySymbol(currencyCode, 'wide'),
      currencyCode,
      digitsInfo,
    );
  }

}
