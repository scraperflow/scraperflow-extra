Monitors blog.fefe.de and sends new blog postings via a telegram bot to a telegram user.

Does not crash or resend a blog post on failure to send a telegram message.

Make sure to set the 'bot-token' key and 'recipient' key correctly.

To fix the keys on startup, create a 'fefe.args' file and define these two keys there:

```
bot-token="..."
recipient="..."
```

To start this workflow, execute `scraperflow` in the same directory as `fefe.yf`. 
If it is not the only `yf` file, execute `scraperflow fefe.yf`instead.

# Dependencies

* [Dev nodes](https://github.com/scraperflow/scraperflow-extra)

# Flow Graph:

![](cfg.png)

# Quickstart Docker:

1. Set `bot-token` to your telegram bot token and `recipient` to your recipient ID
2. Make sure you can receive messages from your telegram bot
3. Use `docker-compose up` to start the workflow
