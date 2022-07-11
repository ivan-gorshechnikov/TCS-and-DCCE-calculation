# TCS DCcE calculation app

## Project goal

The project goal is to create app to calculate TCS, DCcE metrics and show CPU and RAM usage for the period.

TODO: 
make REST service and practice with it

## Technology stack

- Java 11
- Swift 5
- PostgresSQL

## Info
### ScE

**Server Compute Efficiency (ScE)** is a time-based metric. It measures the proportion of time the server spent providing primary services and is expressed as a percentage value. This metric helps in finding the ideal or least used server. Servers with low ScE may be turned off to decrease energy consumption.

![image](uploads/8f121c0096cf5b5e67ea73082d980b7d/image.png)

A server is usually commissioned to provide one or more specific services, which can be primary, secondary and other. If the server performs only one primary task, then CPU utilization = ScE. But if the server performs secondary, tertiary and other tasks in addition to the main task or even instead of it, then CPU utilization is not a sufficiently accurate indicator.

As a general example, the primary service of an email server is the provision of email. The same server can also provide monitoring services, backup services, antivirus services, etc., but these are secondary, tertiary and similar types of services. If the mail server stops receiving email, monitoring, backup, and antivirus services may no longer be required, but the server can still continue to provide them.

Link: https://docshare.tips/green-data-centre-_58b3416bb6d87fc34f8b4853.html

### DCcE

**Data Center Compute Efficiency (DCcE)** is used to measure efficiency of compute resources. It is the ratio of total server compute efficiency and number of servers. Quantifies the efficiency of all computing resources.

![image](uploads/dedb3d97face169393a56ec8463f037a/image.png)

Link: https://docshare.tips/green-data-centre-_58b3416bb6d87fc34f8b4853.html

### TCS

**TCS** (Total Cost of Service) - is the the mirror image of total cost of ownership. If you think of the value that your customer realizes from your product as resulting from the sum of all the work that you do (TCS) *and*
all the work that your customer does (TCO) from raw idea through product delivery to realized benefit, then it becomes clear that creating a disruptive technology is really about taking costs out of the value chain, regardless of which side of the fence they sit on, because you pass your cost savings on to your customer in the form of lower prices.

Every business activity should be examined for the potential to apply the following cost-eliminating techniques:

- Standardization
- Mass personalization
- Process Automation (internal and external leveraging the Internet)
- Customer self-service

Standardization and mass customization are essential to a true multi-tenant SaaS product, but these concepts can be applied to achieve economies-of-scale across the entire value chain.  For example, mass customization can be applied to customer acquisition through website personalization and automated nurturing programs that serve up content to prospects based on their history with your company, i.e., if the prospect spent 5 minutes on the widget page, then send that prospect an invite to watch a video on your widget capabilities. Or, apply Internet-based automation to your external support processes by offering a portal, knowledgebase, forums and online chat.

Customer self-service has the double benefit of automating tasks to drive out costs, but also shifting the cost back to the customer. This may sound like bad business, but the truth is that sometimes your customers would simply rather do things themselves, particularly if it means saving money.

Link to the article with the methodology for calclulating TCS metric: https://www.csp.org.uk/system/files/calculating_the_cost_of_your_service.pdf