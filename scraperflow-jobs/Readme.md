# Example Workflows


## Keyword Monitor

The [Keyword Monitor](https://github.com/scraperflow/scraperflow-extra/tree/master/scraperflow-jobs/keyword-monitor) workflow 
monitors a website for a given set of keywords. 
Once a keyword is found, a notification via a service is triggered.


## News Notification

The [News Notifier](https://github.com/scraperflow/scraperflow-extra/tree/master/scraperflow-jobs/fefe-blog-archiver) workflow monitors 
a website for news. Once a new post is encountered, a notification is issued.


## System Interaction

The [SSH Timeout Ban](https://github.com/scraperflow/scraperflow-extra/tree/master/scraperflow-jobs/ssh-timeout-ban) workflow issues 
system-depdent commands and monitors for SSH timeout commands.
Upon encountering a grace timeout, it issues a system command to ban the IP belonging to the request, after a try threshold is reached.


## Wikipedia HTML Dump

The [Wikipedia HTML Dump](https://github.com/scraperflow/scraperflow-extra/tree/master/scraperflow-jobs/wikipedia-dump) workflow
recursively downloads and archives all HTML sites reachable from a root link. Can be used to archive other websites.
