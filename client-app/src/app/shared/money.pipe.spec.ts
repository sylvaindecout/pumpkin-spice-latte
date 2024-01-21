import {MoneyPipe} from './money.pipe';

describe('MoneyPipe', () => {

  const pipe = new MoneyPipe();

  it('should create', () => {
    const money = {
      amountMinor: 1500,
      currencyUnit: 'EUR',
      scale: 2,
    };

    const result = pipe.transform(money);

    expect(result).toBe('€15.00');
  });

});
