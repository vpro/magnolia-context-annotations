[![Build Status](https://travis-ci.org/vpro/magnolia-context-annotations.svg?)](https://travis-ci.org/vpro/magnolia-context-annotations)
[![Maven Central](https://img.shields.io/maven-central/v/nl.vpro/magnolia-context-annotations.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:"nl.vpro"%20a:"magnolia-context-annotations")
[![javadoc](http://www.javadoc.io/badge/nl.vpro/magnolia-context-annotations.svg?color=blue)](http://www.javadoc.io/doc/nl.vpro/magnolia-context-annotations)


# magnolia-context-annotations
Provides java annotation for executing in system context

## Usage
After installing this module in your magnolia deployment (taking the maven dependency is enough), you 
can do in stead of 
```java
@Override
public String stuff() {
   return MgnlContext.doInSystemContext((MgnlContext.Op<String, Throwable>)
        () -> actualStuff());
}
```
```java
@Override
@MgnlSystemContext
public String stuff() {
   return actualStuff();
}
```

It's a bit like `javax.transaction.Transactional`

The annotation can also be used at class level, so you can quite easily mark all methods of a complete utility class to be executed in system context.


## Installation

```xml
<dependency>
  <groupId>nl.vpro</groupId>
  <artifactId>magnolia-context-annotations</artifactId>
  <version>1.2.0</version>
</dependency>
```
