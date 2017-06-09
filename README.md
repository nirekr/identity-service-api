# identity-service-api
## Description
This repository creates the UUID for an element that has been added to System Definition.
## Documentation
You can find additional documentation for Project Symphony at [dellemc-symphony.readthedocs.io][documentation].
## API overview 
## Before you begin
Verify that the following tools are installed:
 
* Apache Maven 3.0.5+
* Docker 1.12+
* Docker Compose 1.8.0+
* Java Development Kit (version 8)
* RabbitMQ 3.6.6
## Building
Run the following command to build this project:
```bash
mvn clean install
```
## Deploying
The output of running the build step is a tagged Docker image.
 
Run this locally:
```bash
docker run -it --net="host" <docker_image_hash>
```
This deploys a container that communicates with the host's RabbitMQ installation. The container is based on the image created in the build step.
## Contributing
Project Symphony is a collection of services and libraries housed at [GitHub][github].
 
Contribute code and make submissions at the relevant GitHub repository level. See [our documentation][contributing] for details on how to contribute.
## Community
Reach out to us on the Slack [#symphony][slack] channel by requesting an invite at [{code}Community][codecommunity].
 
You can also join [Google Groups][googlegroups] and start a discussion.
 
[slack]: https://codecommunity.slack.com/messages/symphony
[googlegroups]: https://groups.google.com/forum/#!forum/dellemc-symphony
[codecommunity]: http://community.codedellemc.com/
[contributing]: http://dellemc-symphony.readthedocs.io/en/latest/contributingtosymphony.html
[github]: https://github.com/dellemc-symphony
[documentation]: https://dellemc-symphony.readthedocs.io/en/latest/

