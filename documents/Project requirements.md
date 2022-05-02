# **Project requirements document**

**Project title:** *Uhelp*

**Authors:** *Maksym Dmyterko, Danila Prymak, Volha Andrava*

**Date:** *April 28, 2022*

**0. Document versions**

First document version April 30th

**1. Project components (project products)**
- Programming requirements:

Telegram bot application in Java with use of Spring framework

- Nonprogramming requirements:


Project elements should be presented in stages (semesters) according to the planned dates of their completion.

**2. Project boundaries**

- **Integration with Google services**

It was decided to integrate our software with google services to increase comfort from using the app. Due to this integration users will have one complete application and don&#39;t need to go outside of the app to use google services (for example refugee needs to see the location of the hosting apartment which is possible by google maps integrated service which will show the location and route if needed)

- **Implementing Telegram application extension in the form of chat bot, though Telegram seems to be a not first choice option.**

Based on these resources(see below the article), we can see that Telegram right now plays a very important role in providing information about war as digital mass media due to its lack of censorship of information.

President of Ukraine Volodymyr Zelenskyy uses telegram as one the main source of communication with the Ukrainians ([channel in TG](https://t.me/V_Zelenskiy_official)): from rallying global support to disseminating air raid warnings and maps of local bomb shelters.

As far as Telegram bot functionality is enough to implement all planned modules it&#39;s a good choice.

(Sources: [Why Ukrainians turned to Telegram app as Russia invaded](https://indianexpress.com/article/explained/russia-ukraine-war-telegram-app-7847165/),

[How Telegram Became the Digital Battlefield in the Russia-Ukraine War,](https://time.com/6158437/telegram-russia-ukraine-information-war/)[Telegram is the app of choice in the war in Ukraine despite experts&#39; privacy concerns](https://www.npr.org/2022/03/14/1086483703/telegram-ukraine-war-russia?t=1650114416921)

- **What would happen with the app which is refugee help oriented if the war ended?**

If it hopefully happened the target group will be changed and the main module of accommodation searching will work as a hospitality exchange service for people interested in travel (be used by travelers who is seeking for place to stay and travelers who are ready to host them (all for free))


**3. Functional requirements list**

User can
- Find hosts
- Become a host
- Get general information about the current state of the war
- Find links to donation platforms
- Look up list of telegram channels with news on Ukraine
- Change Telegram bot language to Ukrainian, English, or Polish
- Find accommodation using Google maps(Google services integration)

**4. Nonfunctional requirements list**  

- Intuitevly understand the bot design
- Safely store the shared data
- The user experience must be smooth
- Cross-platform support as Telegram is the environment(Android, IOS, Web, Windows, MacOS, Linux)

**5. Project acceptance criteria for first semester**

**Required**

- Deployed prototype of the bot with planned functionality
- Informational portal module
- Basic accommodation searching module implementation

**Expected**

- Database of hosts/accommodations

**6. Measurable deployment indicators**

Chat bot will be available in Telegram 24/7 and have at least 5 users.

**7. Project acceptance criteria for semester two**

Our project acceptance criteria for semester two:

**Required**
* Deploying the application on the server
* Testing the application
* Integration with Google services
* Finishing module of changing languages
* Finishing module of accommodation searching

**Expected**
* Integration with Google translate

**8. Teamwork organization**

*Maksym Dmyterko* – backend(JAVA), testing;

*Danila Prymak* – testing, backend(JAVA);

*Volha Andrava* – testing, documentation, backend(JAVA); - What roles do the team members assume in the project and its development?
- Who oversees communicating with the client and how it is carried out? -- Danila Prymak
- What work method does the team follow? -- *Agile* How has it been chosen? -- *It's the most popular* Have other methods been considered? -- *yes* Why have they been rejected? -- *We want to start with the most popular method, that's widely used in the real world in order to be prepared for the future job*
- What project support tools are in use? -- *JAVA, Spring Boot, SQL*
- How are source codes managed? -- *GitHub*
- How is the project process overseen? -- *Maksym Dmytyerko controls everything*
- What is the diagram of task life cycle in the project (since task inception until completion)?
![picture of agile](https://github.com/realtehcman/Uhelp/blob/main/documents/pictures/agile_pic.jpg?raw=true)

**9. Project risks**
* outdated functionalities

&nbsp;One of the risks is that some of the functionalities may become outdated because of fast change of the situation in Ukraine.

The biggest risk for our bot is that it could become useless in the future because of the end of the war. This will have a big impact on our project because it's main idea is providing help to refugees. At the moment it is impossible to calculate the probability of the end of the war. If this solution will happen we are planning to use our accommodation searching system as a platform for travelers to provide accommodation for each other (couchsurfing - like way) in the form of telegram bot.

* Risk that clients may not be interested in our app
* Risk that we won't manage to integrate our app with Google services
* Risk connected with technologies that we know on an elementary level

**10. Milestones**

### Schedule for the 1st SEMESTR   

|Date       | Description|  
| :------   |  ------ |  
| 31.03.22  | Finishing project vision document  |  
| 30.04.22  | Project requirements delivery |  
| 15.05.22  |Module of informational portal  |  
| 15.06.22  |Module of accommodation searching(at least 50% must be done)  |  
| 15.06.22  |Working prototype that suits the requirements |  


### Schedule for the 2nd SEMESTR   

|Date       | Description|  
| :------   |  ------ |  
| 10.11.22  | Module of integration with Google services  |
| 22.11.22 | Module of changing languages/ finishing module of accommodation searching |  
| 22.11.22  | Deploying app on the server/testing  |  
| 23.01.23  | Delivery of documentation to the committee  |
| 12.2022-01.2023  | Finished application|
