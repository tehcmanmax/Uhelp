# **Project requirements document**

**Project title:** *Uhelp*

**Authors:** *Maksym Dmyterko, Danila Prymak, Volha Andrava*

**Date:** *April 28, 2022*

**0. Document versions**

First document version April 28, 2022

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

- Why does the mobile app work only with Android 25+?
- Why was it decided to develop a web app, although the mobile app would appear to be more suitable?
- Why cannot some data operations be performed through the application? (E.g., users can be added only from the database level.)
- Why was it not decided to integrate with external systems (e.g., authorization systems)?
- Why have some functionalities not been (or are not going to be) implemented, although they do not require significant workload?

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

- **required** – the required criteria must be met so that the product can be accepted by the client. E.g., prototype delivery, implementing specified functionalities, successfully completed load tests, understanding customer processes.
- **expected –** the criteriaexpected by the client, which allow for further system development and its potential implementation. E.g., integration with the client&#39;s internal authorization system, implementation of appropriate functionalities.
- **planned –** the criterianot included in the two groups, but nonetheless expected by the client to be met on the assumption that the project is developed on schedule and the team is capable to meet them. (E.g., system functionalities are not crucial for the project at this stage of its development.) Some of these criteria (e.g., the client expects integration with a niche, undocumented program) cannot be met unless it is within the teams&#39; capabilities

**6. Measurable deployment indicators**

In this paragraph it should be specified, by the semester, how the product is going to be deployed. It is important that the indicators are objectively measurable and quantified to determine the degree of their implementation. The fulfillment of these indicators is assessed in the criterion &quot;Has the project been deployed?&quot;. **Examples** of deployment indicators:

- The system is made available in mojprojekt.com Internet domain and used by at least 70 users.

- The system is made available in the mojprojekt.com Internet domain and is fed with the acquired and developed input data.
- The system is subjected to load tests to ensure that if the system is used by no more than 100 users at the same time, the maximum response time of the server will not exceed 2 seconds.
- At the end of the second semester, the client will receive a beta test system and use it to carry out 25 real / simulated trades.
- In the usability tests carried out at the customer&#39;s site with 5 users, the system will achieve an overall rating of 80%.

**7. Project acceptance criteria for semester two**

Analogous to 6. This section is **required** also in the first semester.

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

In this paragraph all possible (including technology-connected) risks linked to project development should be determined and provided with a course of action plan in the case that they occur. For each risk, please provide: its description, the chance of its occurrence, and the degree of impact on the project. For example:

- In the project we plan to use the xyz machine learning algorithm for face recognition. Since the model has been trained for the Asian market, there is a risk that its efficiency for European projects might be inacceptable. If the recognition efficiency for test sets drops below 75%, abc, def and gih models are going to be introduced, which is going to increase workload by 20 work hours. If those models fail to satisfy the requirements, training an original model may be necessary, which is going to increase the team&#39;s workload by another 120 work hours.
- Architecture fails to respond to the load as expected, and notably the load tests show failure to meet the project acceptance criteria. In such a situation vertical and horizontal scaling mechanisms need to be implemented, which increases the workload on the team by 80 work hours.
- The planned implementation infrastructure (e.g., Firebase) turns out to be too expensive for an engineering project. In such a situation moving the project on to the faculty infrastructure is necessary, and it increases the team&#39;s workload by 80 work hours.
- The client/framer does not show enough interest in the project.

**10. Milestones**

In this paragraph the key milestones for the project should be determined, especially the project release dates and dates of their presentation/submittal to the client.

It is required to plan at least 2 milestones per semester. The milestone of the project should be related to, for example: the release of software with new functionalities, carrying out tests (e.g., acceptance tests at the customer&#39;s site), publishing the system on Google Play, etc. The implementation of the milestone should be documented by the team, the milestone must be presented to the teacher.
