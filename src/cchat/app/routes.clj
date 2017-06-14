
(ns cchat.app.routes
  (:require
    [clojure.java.io :as io]
    [mlib.log :refer [debug info warn]]
    [ring.util.response :refer [redirect not-found]]
    ;
    [compojure.core :refer [GET POST ANY context routes]]
    [compojure.route :as route]

    [mlib.conf :refer [conf]]
    [mlib.http :refer [json-resp text-resp]]
    [mlib.web.middleware :refer [middleware]]

    [cchat.app.chansock :refer [chsk]]))

;

(def WS_CHAT_URI "_ws_chat")


(defn api-404 [req]
  { :status  404
    :headers {"Content-Type" "text/plain"}
    :body    "API endpoint not found"})
;


(defn make-routes [chsk-get chsk-post]
  (routes
    (GET  "/"   _
        (fn [_]
          { :status 200
            :headers {"Content-Type" "text/html;charset=utf-8"}
            :body (io/input-stream (io/resource "public/app/index.html"))}))
    ;
    (GET  WS_CHAT_URI [] chsk-get)
    (POST WS_CHAT_URI [] chsk-post)

    (if (:dev conf)
      (route/files      "/" {:root "tmp/devroot"})
      (route/resources  "/" {:root "public"}))

    (ANY "/*" _  (fn [_] (not-found "Not Found")))))
;


(defn app-routes []
  (->
    (make-routes
      (:ajax-get-or-ws-handshake-fn chsk)
      (:ajax-post-fn chsk))
    middleware))
;

;;.
