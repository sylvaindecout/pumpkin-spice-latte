# Local environment

## Prerequisites

* PostgreSQL is up and running
* Java 17+
* NodeJS 18+

## Configure

### Configure DB

1. Create DB: `handson`
2. Create DB schema: `handson`
3. Create user with username `user` and password `user123`

### Configure runner for modular monolith

1. Select main class: `io.shodo.pumpkin.monolith.App`

### Configure runner for staff simulator

1. Select main class: `io.shodo.pumpkin.staff.SimulationApp`
2. Configure environment variables: `SERVER_PORT=9090`

## Run

1. Run modular monolith: use runner
2. Run client app: `(cd client-app; npm run start-local)`
3. Run staff-simulator: use runner
