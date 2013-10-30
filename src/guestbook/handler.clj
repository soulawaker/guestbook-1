(ns guestbook.handler
  (:use guestbook.routes.home
        compojure.core)
  (:require [noir.util.middleware :as middleware]
            [compojure.route :as route]
            [guestbook.models.schema :as schema]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (if-not (schema/initialized?) (schema/create-tables))
  (println "guestbook started successfully..."))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (println "shutting down..."))

;;append your application routes to the all-routes vector
(def app
 (middleware/app-handler
   [auth-routes home-routes app-routes]
   :middleware
   []
   :access-rules
   []
   :formats
   [:json-kw :edn]))
