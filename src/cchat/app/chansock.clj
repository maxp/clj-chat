
(ns cchat.app.chansock
  (:require
    [clojure.core.async :refer [thread <!! close!]]
    ;
    [mount.core :refer [defstate]]
    [taoensso.sente :refer [make-channel-socket!]]
    [taoensso.sente.server-adapters.http-kit :refer [get-sch-adapter]]
    ;
    [mlib.log :refer [debug]]))
;


(comment
  ch-recv         ;; async/chan receive
  send-fn         ;; (fn [user-id event])
  connected-uids) ;; atom
;

(defstate chsk
  :start
    (make-channel-socket! (get-sch-adapter) {}))
;

(defn start []
  (thread
    (let [{:keys [ch-recv send-fn connected-uids]} chsk]
      (prn "chsk:" chsk)
      (loop []
        (when-let [event (<!! ch-recv)]
          (prn "event:" event)
          (prn "uids:" @connected-uids)
          ; (doseq [uid (:any @connected-uids)]
          ;   (send-fn uid event)))))))
          (recur)))
      (debug "fanout loop stopped"))))
;

(defn stop []
  (when-let [ch (:ch-recv chsk)]
    (close! ch)))
;

(defstate chat-handler
  :start
    (start)
  :stop
    (stop chat-handler))

;;.
