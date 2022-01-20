## Build tools & versions used
compile version: 31
minSDK: 21
target SDK: 31

## Steps to run the app
Clone the github project and once all libraries sync in android studio, should be able to build the app

## What areas of the app did you focus on?
1. Archiecture pattern(MVVM i am using), easier to work on new features
2. I am using a little bit of OOP to separate the different UI on the list, for example, I created list of Displayables, and pass it into adapter, 
the adapter will use the type(DisplayableType) to create different UI, in this case, header ui and employee UI.
3. Put relavent files into relavent packages to make the project clear and easy to work on
4. Avoid Memory leak


## What was the reason for your focus? What problems were you trying to solve?
1. Good archiecture pattern can be easier to work on new features and write unit tests(which I'd like to but due to the time limit, didnt write tests for now)
2. OOP allows us to focus on the feature instead of working on the existing code base which will make the code header to maintain in the long run
3. Good file structure makes people understand our code in a high level
4. Memory leak is a big thing and can gives us lots of headache and cause lots of strange bugs when in production where different users have different use cases

## How long did you spend on this project?
day 1: 19:00 - 21:00 for 2 hours finished all the basic structure, from api, json data, viewmodels, activity, adapter, simple UI
day 2: 11:45 - 13:00 for 1.5 hours finished the rest, including sort, group by team and employee type , 
  created different UI for different view types(header and employee),  refresh button, error handling
  13:00 - 13:20 for 20 min, publish code to the github and write the README

## Did you make any trade-offs for this project? What would you have done differently with more time?
Trade-offs due to time limits
1. Dependency injection, I will usually use dagger
2. Adding unit test to verify the logic, like sort, group by etc.
3. For different viewholders on the adapters, currently all the viewholders are in the same file, if we are creating more
UI types, the adapter will be super large, so will need to create a managnent file to manage all the viewholder files, and 
when adding new viewholder file, this can be in a separate file

## What do you think is the weakest part of your project?
No testing which is easy to create bug as code base grows

## Did you copy any code or dependencies? Please make sure to attribute them here!
Libraries I am using: RxJava, RxKotlin, Retrofit, OKHttp

## Is there any other information you’d like us to know?
Overall I like the idea of giving lots of space for me to write code in a more flexable way in terms of thinking the trade-offs and different use cases.




