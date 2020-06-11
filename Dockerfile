FROM jdk:1.8

ADD generator-0.0.1-SNAPSHOT-release.tar.gz /usr/local/

ENTRYPOINT ["/usr/local/generator-0.0.1-SNAPSHOT/bin/start.sh"]

