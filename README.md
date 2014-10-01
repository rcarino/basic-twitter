# Basic Twitter
Simple twitter clone.

Time spent: 15 hours spent in total

Completed user stories:

 * [x] Required: User can view a list of latest box office movies including title, cast and tomatoes rating
 * [x] Required: User can click on a movie in the list to bring up a details page with additional information such as synopsis
    * [x] Required: User should be displayed the username, name, and body for each tweet
    * [x] Required: User should be displayed the relative timestamp for each tweet "8m", "7h"
    * [x] Required: User should be displayed the relative timestamp for each tweet "8m", "7h"
    * [x] Optional: Links in tweets are clickable and will launch the web browser (see autolink)
 * [x] Required: User can compose a new tweet
    * [x] Required: User can click a “Compose” icon in the Action Bar on the top right
    * [x] Required: User can then enter a new tweet and post this to twitter
    * [x] Required: User is taken back to home timeline with new tweet visible in timeline
    * [x] Optional: User can see a counter with total number of characters left for tweet
 * [x] Advanced: User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)
 * [x] Advanced: User can open the twitter app offline and see last loaded tweets
 * [x] Advanced: Improve the user interface and theme the app to feel "twitter branded"

Notes:
Bulk of time went into debugging active android. Couldn't get foreign keys to behave properly, so ended up
manually coding logic to avoid inserting duplicates.

Walkthrough of all user stories:

![Video Walkthrough](anim_basictwitter.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).