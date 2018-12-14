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
}
```

It's a bit like `javax.transaction.Transactional`


## Installation

```xml
<dependency>
  <groupId>nl.vpro</groupId>
  <artifactId>magnolia-context-annotations</artifactId>
  <version>1.0</version>
</dependency>
```
