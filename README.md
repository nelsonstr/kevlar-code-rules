# kevlar-code-rules

This is the [kevlar-code-rules](http://nelsonstr.github.io/kevlar-code-rules/).


[![Build Status](https://travis-ci.org/nelsonstr/kevlar-code-rules.svg?branch=master)](https://travis-ci.org/nelsonstr/kevlar-code-rules)

## Releasing

* Make sure `gpg-agent` is running.
* Execute `mvn -B release:prepare release:perform`

For publishing the site do the following:

```
cd target/checkout
mvn verify site site:stage scm-publish:publish-scm
```