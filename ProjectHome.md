# Usage #

After you defined a `wft` database (with `root` user and no password) in your MySQL instance (["jdbc:mysql://localhost:3306/wft"](http://code.google.com/p/web-foot-tournament/source/browse/trunk/wft/wft-zk-ui/src/main/webapp/WEB-INF/datasource_mysql.xml)), just run

**`mvn install`**in the root folder**`trunk/wft/`**

**`mvn jetty:run`**in the webapp folder**`trunk/wft/wft-zk-ui`**

And then, go to http://localhost:8888/wft-zk-ui/ !
