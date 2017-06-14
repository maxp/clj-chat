
(ns cchat.app.srv
  (:require
    [mlib.log :refer [debug info warn]]
    ; [ring.adapter.jetty :refer [run-jetty]]
    [org.httpkit.server :refer [run-server]]

    [mount.core :refer [defstate]]
    ;
    [mlib.conf :refer [conf]]

    [cchat.app.routes :refer [app-routes]]))
;

(defn start [handler]
  (let [hc (:http conf)]
    (info "build -" (:build conf))
    (info "start server -" hc)
    (run-server handler hc)))
;

(defstate server
  :start
    (start (app-routes))
  :stop
    (when server
      (server)))
;

;;.
