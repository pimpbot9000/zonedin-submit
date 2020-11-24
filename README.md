# Zonedin-submission
Final submission for the Java Palvelinohjelmointi -course. It's supposed to be some king of LindedIn -mockup. I decided to name it "ZonedOut", haha.
It's a Spring Boot app, mostly using the post-redirect-get pattern so there's a lot of reloading. Not very dynamic.

Since I was kinda running out of time I did not implement the following features:

- There's no like button for messages.
- There are no skills nor skill endorsements.
- The "feed" aka the "message board" only shows the latest 20 messages, there's no way to see older messages.

There is some basic jquery/axios-magic to make the users home-view dynamic and there's some sort of RESTfull-ish backend stuff there to support it, but as I said
most of the app is static stuff, based on simple template rendering and post-redirect-get -pattern.

### Deployment

The project is hosted in Heroku, one can find it [here](https://dreadful-skull-05130.herokuapp.com).

### Usage

In the Heroku deployment there are three different test-users, with usernames: ```user1```, ```user2``` and ```user3```. The password for each previously mentioned user is ```12345```.

When running the app locally use the ```dev``` profile.

### Disclaimer

Regarding the test user accounts: Any resemblance to actual living and/or dead dictators are purely coincidental.

### About security issues

The app has it's fair share of authorization issues. That's because the grading criteria did not put virtually any emphasis on this, albeit authorization being very important... I just did not care

```
@PostMapping("/feed/reply/{postId}")
public String postReply(Authentication auth, Model model, @PathVariable Long postId, @RequestParam String content){        
    feedService.addReply(userAccountService.getUserAccount(auth.getName()), content, postId);
    return "redirect:/feed";
}
```

Above is a fine specimen. In the essence if user is authenticated there are no checks what so ever if the user has the authorization to post a reply to the post. Oh well... :/
