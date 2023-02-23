This library is an extension for maven. It allows to download artifact from 
a basic http server, non fully maven compliant which stores artifact 
with the same path structure but without any index files. 

This extension will attempt the download of 
```com.example:my-artifact:1.2.3``` is 
```<repository_url>/com/example/my-artifact/1.2.3/my-artifact-1.2.3.jar```
# Maven compatibility
It's build for maven 3.9

# How to use it
Add an extension in your project:
```xml
<build>
    <extensions>
        <extension>
            <groupId>fr.exygen</groupId>
            <artifactId>maven-http-directory</artifactId>
            <version>1.0-SNAPSHOT</version>
        </extension>
    </extensions>
```
Add a repository
```xml
<repositories>
    <repository>
        <url>http://my-server/repository/path/</url>
        <layout>http-directory</layout>
        <id>myserver</id>
    </repository>
```

Add your dependencies, just as usual:
```xml
<dependencies>
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>my-artifact</artifactId>
        <version>1.2.3</version>
        <scope>compile</scope>
    </dependency>
	
```
