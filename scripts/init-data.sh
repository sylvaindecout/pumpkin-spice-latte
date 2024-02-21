#! /usr/bin/env ash

set -eo pipefail

curl -X PUT "http://modular-monolith:8080/menu/drinks/Latte" \
    -H  "accept: application/json" \
    -H  "Content-Type: application/json" \
    -d '{"unitPrice":{"amountMinor":500,"currencyUnit":"EUR","scale":2},"ingredients":{"Coffee beans":{"amount":7000,"unitOfMeasure":"mg"},"Milk":{"amount":50,"unitOfMeasure":"mL"}}}'

curl -X PUT "http://modular-monolith:8080/menu/drinks/Espresso" \
    -H  "accept: application/json" \
    -H  "Content-Type: application/json" \
    -d '{"unitPrice":{"amountMinor":300,"currencyUnit":"EUR","scale":2},"ingredients":{"Coffee beans":{"amount":10000,"unitOfMeasure":"mg"}}}'

curl -X PUT "http://modular-monolith:8080/stock/ingredients/Coffee%20beans" \
    -H  "accept: application/json" \
    -H  "Content-Type: application/json" \
    -d '{"currentQuantity":{"amount":1000,"unitOfMeasure":"g"}}'

curl -X PUT "http://modular-monolith:8080/stock/ingredients/Milk" \
    -H  "accept: application/json" \
    -H  "Content-Type: application/json" \
    -d '{"currentQuantity":{"amount":1000,"unitOfMeasure":"cL"}}'
