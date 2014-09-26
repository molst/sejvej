(defproject sejvej "0.0.1-SNAPSHOT"
  :description "a file server"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  ;;:url "https://github.com/molst/annagreta"
  ;;:scm {:name "git" :url "https://github.com/molst/annagreta"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.2.2"]
                 [ring/ring-devel "1.2.2"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [net.cgrand/moustache "1.1.0"]
                 [com.novemberain/pantomime "1.4.0"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}}
  :main sejvej.site)