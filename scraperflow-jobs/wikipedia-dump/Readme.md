Generates a static html dump of wikipedia, slowly.

Can be used as a skeleton to archive other sites recursively.

Speed is controlled by using local connection vs proxies and the `holdOnReservation` configuration for the Http request.

Progress-preserving, restart is possible at any time.

# Quickstart - Docker

        docker-compose up

# Dependencies

* [Dev nodes](https://github.com/scraperflow/scraperflow-extra)

# Flow Graph:

![](cfg.png)
