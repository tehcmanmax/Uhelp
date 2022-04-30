# **Project requirements document**

**Project title:** *Uhelp*

**Authors:** *Maksym Dmyterko, Danila Prymak, Volha Andrava*

**Date:** *April 28, 2022*

**0. Document versions**

First document version April 28, 2022; 2nd -- April 29th; 3rd -- April 30th

**1. Project components (project products)**

**All** programming and documented nonprogramming components of the project should be unambiguously and precisely defined. **Examples** of programming components are:

- Android 25+ mobile apps



- Django-framework-based Python app for servers


- Database server instance based on MSSQL engine

**Examples of** nonprogramming components are:

- Functional specification,
- Prototype made with Adobe XD tool,
- Usability tests reports,
- Mechanical robot created for the project and its construction scheme,
- Documented comparative analysis of technology performance for justifying technological decisions.

Project elements should be presented in stages (semesters) according to the planned dates of their completion.

**2. Project boundaries**

In this paragraph the full range of functionalities and the client&#39;s expectations should be critically examined. It should be determined (with justification) what functional and non-functional conditions are expected to be met by the client but will not be implemented during the project. Examples of questions that may be helpful for the analysis are **:**

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
- Change Telegram bot language to Russian, English, or Polish
- To integrate with Google services: Google maps, translator

**4. Nonfunctional requirements list**  

- Intuitevly understand the bot design
- Safely store the shared data
- The user experience must be smooth

**5. Project acceptance criteria for first semester**

In this paragraph the process of accepting project products after semester one should be explained. The criteria for product acceptability also should be specified.

In the case of functional requirements, non-functional requirements and deployment indicators, reference should be made to those specified in sections 3, 4 and 6 (indicating, however, in words their significance for increasing readability).

The product acceptability criteria should be presented in three categories:

- **required**

- Deployed prototype of the bot with planned functionality
- Informational portal module
- Basic accommodation searching module implementation

- **expected**

- Database of hosts/accommodations

**6. Measurable deployment indicators**

In this paragraph it should be specified, by the semester, how the product is going to be deployed. It is important that the indicators are objectively measurable and quantified to determine the degree of their implementation. The fulfillment of these indicators is assessed in the criterion &quot;Has the project been deployed?&quot;. **Examples** of deployment indicators:

- The system is made available in mojprojekt.com Internet domain and used by at least 70 users.

- The system is made available in the mojprojekt.com Internet domain and is fed with the acquired and developed input data.
- The system is subjected to load tests to ensure that if the system is used by no more than 100 users at the same time, the maximum response time of the server will not exceed 2 seconds.
- At the end of the second semester, the client will receive a beta test system and use it to carry out 25 real / simulated trades.
- In the usability tests carried out at the customer&#39;s site with 5 users, the system will achieve an overall rating of 80%.

**7. Project acceptance criteria for semester two**

Our project acceptance criteria for semester two:
Required
* Deploying the application on the server
* Testing the application 
* Integration with Google services
Expected

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
![picture of agile](https://github.com/realtehcman/Uhelp/blob/main/documents/agile_pic.jpg?raw=true)

**9. Project risks**

One of the risks is that some of the functionalities may become outdated because of fast change of the situation in Ukraine.

The biggest risk for our bot is that it could become useless in the future because of the end of the war. This will have a big impact on our project because it's main idea is providing help to refugees. At the moment it is impossible to calculate the probability of the end of the war. If this solution will happen we are planning to use our accommodation searching system as a platform for travellers to provide accommodation for each other (couchsurfing - like way) in the form of telegram bot.

Another risk is that clients may not be interested in our app.

**10. Milestones**

In this paragraph the key milestones for the project should be determined, especially the project release dates and dates of their presentation/submittal to the client.

It is required to plan at least 2 milestones per semester. The milestone of the project should be related to, for example: the release of software with new functionalities, carrying out tests (e.g., acceptance tests at the customer&#39;s site), publishing the system on Google Play, etc. The implementation of the milestone should be documented by the team, the milestone must be presented to the teacher.
