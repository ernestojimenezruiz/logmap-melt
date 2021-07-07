# LogMap interface for the MELT platform

This maven project contains the interface (extending `MatcherURL` or `MatcherYAAAOwlApi` depending on the selected `pom` file) used by [LogMap](https://github.com/ernestojimenezruiz/logmap-matcher) to communicate with the [MELT platform](https://github.com/dwslab/melt) and participate in the [OAEI campaign](http://oaei.ontologymatching.org/) (from 2021). 


## Instructions

- Install maven and docker. Docker should also be running in your system.
- Clone [LogMap's repository](https://github.com/ernestojimenezruiz/logmap-matcher)
- Follow the instruction to install additional LogMap dependencies not available in Maven central repository.
- Execute `mvn install` in the cloned LogMap's folder. 
- Clone this repository
- Execute `mvn clean install` (in Linux you may need root privileges when invoking/using docker)
  - This will generate a docker image (e.g., `logmap-melt-oaei-2021-web-latest.tar.gz`) that you can use with the [MELT client](https://dwslab.github.io/melt/matcher-evaluation/client) or from [Java](https://dwslab.github.io/melt/matcher-packaging/web#evaluate-and-re-use-a-web-interface-matcher-in-melt). An example is also given [here](https://github.com/ernestojimenezruiz/logmap-melt/tree/main/src/test/java/uk/ac/city/oaei/melt).
  - Generated images (extending `MatcherURL` or `MatcherYAAAOwlApi`) available [here](https://tinyurl.com/logmap-melt-images).
- Logs stored in the file `logmap-log.out` (see [log4j.properties](https://github.com/ernestojimenezruiz/logmap-melt/blob/main/src/main/resources/log4j.properties)).
- Debugging tips:
  - It is convenient to properly set up log4j (see for example [log4j.properties](https://github.com/ernestojimenezruiz/logmap-melt/blob/main/src/main/resources/log4j.properties)).
  - In the test class provided [here](https://github.com/ernestojimenezruiz/logmap-melt/tree/main/src/test/java/uk/ac/city/oaei/melt) do not close the `MatcherDockerFile` so that you can access internal logs of your system with `docker container logs {id_container}`. The id of the container will appear in the main log when running the test class.



