[![Tests](https://github.com/sylvaindecout/pumpkin-spice-latte/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/sylvaindecout/pumpkin-spice-latte/actions/workflows/build.yml) [![Gitmoji](https://img.shields.io/badge/gitmoji-%20%F0%9F%98%9C%20%F0%9F%98%8D-FFDD67.svg)](https://gitmoji.dev)

![Pumpkin Spice Latte](./client-app/src/assets/banner.svg)

This application is used to take orders from customers.

## Check out the [Wiki](https://github.com/sylvaindecout/pumpkin-spice-latte/wiki)

## Architecture

```mermaid
C4Container
  Person_Ext(customer, "Customer", "A customer ordering drinks at the counter")
  Container_Boundary(container, "") {
    Container(client, "Client app", "TypeScript, Angular", "fixme")
    Container(simulator, "Staff simulator", "Kotlin, Spring Boot", "Simulate actions performed by the staff")
    Container_Boundary(monolith, "Modular monolith") {
      Container(ordering, "Ordering", "Kotlin, Spring Boot", "Process orders from customers")
      Container(menu, "Menu", "Kotlin, Spring Boot", "Full menu, including prices")
      SystemQueue(preparation, "Drink preparation", "Kotlin, Spring Boot", "List of drinks pending preparation")
      Container(stock, "Stock", "Kotlin, Spring Boot", "Stock of ingredients")
      ContainerDb(menu_db, "Menu DB", "PostgreSQL", "")
      ContainerDb(stock_db, "Stock DB", "PostgreSQL", "")
    }
  }
  System_Ext(staff, "Staff", "All actions performed by the staff")
  Rel(menu, menu_db, "Uses")
  Rel(stock, stock_db, "Uses")
  Rel(customer, client, "Uses")
  Rel(client, menu, "Uses", "REST")
  Rel(client, stock, "Uses", "REST")
  Rel(client, preparation, "Uses", "REST")
  Rel(client, ordering, "Uses", "REST")
  Rel(ordering, menu, "Uses", "REST")
  Rel(ordering, stock, "Uses", "REST")
  Rel(ordering, preparation, "Uses", "REST")
  Rel(staff, preparation, "Uses", "REST")
  Rel(simulator, preparation, "Uses", "REST")
```

<picture>
  <source media="(prefers-color-scheme: light)" srcset="doc/images/light-c4_container.svg" />
  <img src="doc/images/dark-c4_container.svg" alt="C4 model - Container diagram" title="C4 model - Container diagram" />
</picture>

## Usage

* Run tests for client app: `(cd client-app; npm run test-local)`
* Run tests for back-end: `./gradlew test`
* Run mutation coverage: `./gradlew pitest`
* Start application:
  1. Build packages (backend): `./gradlew build --exclude-task test`
  2. Run on local environment: `docker compose up -d`
  3. Client app can be accessed at http://localhost:4200
  4. Tear down local environment: `docker compose down`

## Guides

* [How to configure local environment](./doc/local_env.md)
* CI/CD
  * How to deploy
  * How to validate deployment
  * How to rollback deployment
* GDPR
  * How to collect personal data
  * How to erase personal data

## Contributing

* [Working practices](https://github.com/sylvaindecout/pumpkin-spice-latte/wiki/Working-practices)
* [Style guide](./doc/style_guide.md)
