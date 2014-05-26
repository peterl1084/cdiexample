# Vaadin Java EE app example

This is a work in progress Vaadin CDI example project that could be a starting point for a larger Java EE app.

Stuff that this app is built on:

 * [Vaadin](https://vaadin.com/)
 * [Java EE](http://www.oracle.com/technetwork/java/javaee/overview/index.html)
 * [JPA](http://en.wikipedia.org/wiki/Java_Persistence_API)
 * [Apache Shiro](http://shiro.apache.org)
 * [Vaadin CDI](http://vaadin.com/addon/vaadin-cdi)
 * [CDI events](http://docs.oracle.com/javaee/6/tutorial/doc/gkhic.html)
 * [MVP](http://en.wikipedia.org/wiki/Model–view–presenter)
 * The [new Valo theme for Vaadin](https://vaadin.com/blog/-/blogs/7-series) to make it look modern
 
TODO:

 * Limited UI for customers to edit their own details
 * Localization
 * Clean up, blog posts etc.

## To get started (plaining with this app in your dev environment):

Build should be IDE/platform indendent. So just

 * Checkout the the project with `git clone https://github.com/peterl1084/cdiexample.git`.
 * (OPTIONAL) define a datasource and configure it in ```backend/src/main/resources/META-INF/persistence.xml```. Development friendly Java EE servers like TomEE, WildFly and GlassFish will do this automatically for you, as we haven't defined ```<jta-data-source>``` in ```persistence.xml```
 * Build + Run/Debug in your favorite IDE
 * ... or use ```mvn install; cd ui; mvn tomee:run``` to launch it in TomEE without any configuration


