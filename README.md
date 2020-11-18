# T-Shirt Planning (Scrum) Poker

This app allows you to estimate issues with your teammates remotely without any initial setup, loading user
stories or signing in.

![Main Screen](.repo/mainscreen.png?raw=true)
[Try it in demo room](https://verkhovin.github.io/poker/rooms/1)

So far, only T-Shirt sizing estimates are available as I use only them at work. I'm looking forward to finding time to implement other types of 
estimates there (story points primarily).

You can read more about scrum poker and T-Shirt sizes [here](https://www.c-sharpcorner.com/article/agile-story-point-estimation-techniques-t-shirt-sizing/#:~:text=What%20is%20T%2Dshirt%20sizing,%2C%20M%2C%20L%2C%20XL).

## How to use
1. Go to https://verkhovin.github.io/poker/.
2. Click to **Create a new room** or if you have the room's id, enter the  existing room by entering it to 
corresponding field and clicking **Enter the room**.
3. If you are for the first time here, enter your name. It will be used to identify you
4. After entering the room, send the room's id (or direct link) to your teammates.
5. Discuss an issue with your teammates. When all the participants give their estimates, click **Show**.
6. Click *Clear* for the next round.

## Tech info
It's a simple web application with Java + Spring Boot backend and frontend implemented with Vue.js. Frontend
communicates with the backend through minimalistic REST API and sockets.

Frontend and backend are split into separate submodules that are built by Maven.

Simple CI is configured by Travis:
* starts `mvn install` to build all the artifacts
* deploys backend to heroku
* commits frontend artifacts to [Github Pages Repository](https://github.com/verkhovin/poker) 

Technologies: Java, Spring Boot, Spring Data JPA, PostgreSQL, Vue.js, Bootstrap, Websockets, REST API

