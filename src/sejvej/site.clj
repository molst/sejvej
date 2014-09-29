(ns sejvej.site
  (:use [net.cgrand.moustache :exclude [not-found]])
  (:use ring.middleware.params)
  (:use [ring.adapter.jetty :only [run-jetty]])
  (:use [ring.middleware.stacktrace :only [wrap-stacktrace wrap-stacktrace-web]])
  (:use [ring.middleware.content-type])
  (:use [ring.util.response :only [response file-response]])
  (:use pantomime.mime)
  (:import [java.lang System])
  (:gen-class))

(def jetty (atom nil))

(def routes
  (-> (app
       ["hello"] (app wrap-params :get (fn [req] {:body (str "world, serving files from pwd: " (System/getProperty "user.dir"))}))
       
       [& file-path]
       (fn [req]
         (let [file-path-str (apply str (interpose "/" file-path))
               mime-type (mime-type-of file-path-str)]
           (if (= mime-type "text/html")
               (response (slurp file-path-str))
               (file-response file-path-str {:root (System/getProperty "user.dir")})))))
   (wrap-stacktrace)))

(defn -main []
  (reset! jetty (run-jetty #'routes {:port 4444 :join? false}))
  (println "port 4444 serving files from pwd: " (System/getProperty "user.dir")))