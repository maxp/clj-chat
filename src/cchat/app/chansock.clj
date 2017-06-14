
(ns cchat.app.chansock
  (:require
    [clojure.core.async :refer [thread]]
    ;
    [mount.core :refer [defstate]]
    [taoensso.sente :refer [make-channel-socket!]]
    [taoensso.sente.server-adapters.http-kit :refer [get-sch-adapter]]))
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
    (let {:keys ch-recv send-fn connected-uids} chsk
      (prn "chsk:" chsk)
      (while true
        (when-let [event (<!! ch-recv)]
          (prn "event:" event)
          (prn "uids:" @connected-uids))))))
          ; (doseq [uid @connected-uids]
          ;   (send-fn uid event)))))))
;

(defstate chat-handler
  :start
    (start))

;;.
