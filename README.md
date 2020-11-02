# Zonedin-submission
Final submission for the Java Palvelinohjelmointi -course. It's supposed to be some king of LindedIn -mockup. I decided to name it "ZonedOut", haha.
It's a Spring Boot app, mostly using the post-redirect-get pattern so there's a lot of reloading. Not very dynamic.

Since I was kinda running out of time I did not implement the following features:

- There's no like button for messages.
- There are no skills nor skill endorsements.
- The "feed" aka the "message board" only shows the latest 20 messages, there's no way to see older messages.

There is some basic jquery/axios-magic to make the users home-view dynamic and there's some sort of RESTfull-ish backend stuff there to support it, but as I said
most of it is static stuff.

### Deployment

The project is hosted in Heroku, one can find it [here](https://dreadful-skull-05130.herokuapp.com).

### Usage

In the Heroku deployment there are three different test-users, with usernames: ```user1```, ```user2``` and ```user3```. The password for each user is ```12345```.

### Disclaimer

Regarding the test user accounts: Any resemblance to actual living and/or dead dictators are purely coincidental.
