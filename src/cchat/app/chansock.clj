
(ns cchat.app.chansock
  (:require
    [clojure.core.async :refer [thread <!! close!]]
    ;
    [mount.core :refer [defstate]]
    [taoensso.sente :refer [make-channel-socket!]]
    [taoensso.sente.server-adapters.http-kit :refer [get-sch-adapter]]
    ;
    [mlib.log :refer [debug error]]))
;


(defn user-id-fn [params]
  (:client-id params))
;

(comment
  ch-recv         ;; async/chan receive
  send-fn         ;; (fn [user-id event])
  connected-uids) ;; atom
;

(defstate chsk
  :start
    (make-channel-socket!
      (get-sch-adapter)
      {:user-id-fn user-id-fn}))
;

(defn start []
  (thread
    (let [{:keys [ch-recv send-fn connected-uids]} chsk]
      (loop []
        (when-let [data (<!! ch-recv)]
          (let [event (:event data)]
            (condp = (first event)
              ;:trans/chat
              :trans/buff64
                (doseq [uid (:any @connected-uids)]
                  (prn "uid:" uid event)
                  (send-fn uid event))
              nil))
              ;; (error "unexpected event:" event)))
          (recur)))
      (debug "fanout loop stopped"))))
;

(defn stop [_]
  (when-let [ch (:ch-recv chsk)]
    (close! ch)))
;

(defstate chat-handler
  :start
    (start)
  :stop
    (stop chat-handler))

;;.
