

## Description

This repository contains the source code for common-contract-enricher, common-testing, and common-rabbitmq.

* common-contract-enricher helps with the generation of classes from the API Java Shared Data (JSD) files.

* common-testing is a tiny artifact used as a test dependency to provide small utilities for JUnit testing purposes (such as message file reader function)
The testing dependency import should use the scope import:
```bash
<scope>import</scope>
```

* common-rabbitmq is a library of AMQP-related functions including but not limited to:
  * message aggregator
  * base Spring configuration
  * basic messaging validation checks
  * default retry policy
  * basic error handling

## Documentation
You can find additional documentation for Project Symphony at [dellemc-symphony.readthedocs.io](https://dellemc-symphony.readthedocs.io).

## Before you begin
Verify that the following tools are installed:

* Apache Maven 3.0.5+
* Java Development Kit (version 8)

## Building
Run the following command to build this project:
```bash
mvn clean install
```
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
