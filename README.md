# Notes Recaller

## Description

Is a service that sends you a daily email with a random highlights from your notes database that you can create.

## Installation

1. Clone the repository
2. Install the dependencies
3. Create a `application.properties` file similar to the `application-dev.properties` with values from your `SendGrid`, `CloudProvider` and notes `database`.
4. Run the application

## Setup locally

1. Create a note or parse a kindle notes file _(using [Bookcision](https://readwise.io/bookcision) for example)_.
2. Add the subscribers in the database using the `POST /api/v1/subscribers` endpoint. 
3. Setup a cron job to run the application daily and call the `POST api/v1/subscribers/send-mail` or just call it manually.
4. Enjoy your daily email with random highlights from your notes.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

