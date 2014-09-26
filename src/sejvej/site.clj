(ns sejvej.site
  (:use [net.cgrand.moustache :exclude [not-found]])
  (:use ring.middleware.params)
  (:use [ring.adapter.jetty :only [run-jetty]])
  (:use [ring.middleware.stacktrace :only [wrap-stacktrace wrap-stacktrace-web]])
  (:use [ring.util.response :only [status redirect resource-response not-found]])
  (:use pantomime.mime)
  (:import [java.lang System]))

(def jetty (atom nil))

(def routes
  (-> (app
       ["hello"] (app wrap-params :get (fn [req] {:body (str "world, serving files from pwd: " (System/getProperty "user.dir"))}))
       
       [& file-path]
       (fn [req]
         (let [file-path-str (apply str (interpose "/" file-path))
               file-res (resource-response file-path-str {:root (System/getProperty "user.dir")})]
           (assoc file-res :headers {"Content-Type" (mime-type-of file-path-str)}))))
   
   (wrap-stacktrace)))

(defn -main []
  (reset! jetty (run-jetty #'routes {:port 4444 :join? false}))
  (println "port 4444 serving files from pwd: " (System/getProperty "user.dir")))