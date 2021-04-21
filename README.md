# Global

Splash:
I make a request to the local host to fetch next_path here. Then I pass that data to home fragment.

Home:
I make a request to the local host with the path received from Splash. On this page,
I display the response I get from local host. I also save times_fetched to local storage.

Overall Architecture:

I used MVVM architecture.

1 - Fragment tells the view model that the button is clicked.

2 - View model ask repository for data.

3 - Repository goes to the remote data source to fetch data from local host.
    If we were to store "times_fetched" in local database using Room, then repository
    could also go to local data source.

4 - Remote data source fetches data from local host. Send it back to repository.

5 - Repository sends data back to view model.

6 - View model holds state as StateFlow which is collected from the fragment. State value in
    view model is set according to the response data.

7 - Flow is collected from fragment with respect to its lifecycle. And the data is displayed