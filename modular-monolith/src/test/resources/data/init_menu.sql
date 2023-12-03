INSERT INTO drink (name, unit_price, currency, recipe) VALUES
    ('Latte', 500, 'EUR', '{
        "Coffee beans": { "amount": 7000, "unitOfMeasure": "mg" },
        "Milk": { "amount": 50, "unitOfMeasure": "mL" }
    }'),
    ('Espresso', 300, 'EUR', '{
        "Coffee beans": { "amount": 10000, "unitOfMeasure": "mg" }
    }');