FROM openjdk:13

COPY scraperflow /scraperflow-src
WORKDIR /scraperflow-src
RUN /scraperflow-src/gradlew disttar
RUN mkdir -p /scraperflow-src/out
RUN tar xf /scraperflow-src/build/distributions/scraper*.tar -C /scraperflow-src/out
RUN cp /scraperflow-src/out/scraper*/lib/*.jar /bin
WORKDIR /rt
RUN rm -rf /scraperflow-src
RUN touch /run.sh
RUN chmod +x /run.sh
# create file in image to not have the file in git repo
RUN echo '#!/bin/sh' >> /run.sh
RUN echo 'exec java -p /bin:/nodes:/plugins:/addons --add-modules ALL-MODULE-PATH -m scraper.app runtime-nodes:/runtime-nodes "$@"' >> /run.sh
ENTRYPOINT ["/run.sh"]
