
(ns cchat.ui.trans
  (:require-macros
    [cljs.core.async.macros :as asyncm :refer [go go-loop]])
  ;
  (:require
    [cljs.core.async :as async :refer [<! >! put! chan]]
    [taoensso.sente :refer [make-channel-socket! cb-success?]]
    ;
    [cchat.ui.state :refer [*nick *text *chat]]))
;

;; NOTE: duplicated constant in app/routes.clj
(def WS_CHAT_URI "/_ws_chat")


(let [{:keys [chsk ch-recv send-fn state]}
      (make-channel-socket! WS_CHAT_URI {:type :auto})]
  ;
  (def chsk       chsk)
  (def ch-chsk    ch-recv) ; ChannelSocket's receive channel
  (def chsk-send! send-fn) ; ChannelSocket's send API (fn [event & [?timeout-ms ?cb-fn]]))
  (def chsk-state state))   ; Watchable, read-only atom

;


(defn send-text [nick text]
  (.log js/console "send-text:" nick text)
  (chsk-send! [:trans/chat {:nick nick :text text}]))

;


; (chsk-send! ; Using Sente
;   [:some/request-id {:name "Rich Hickey" :type "Awesome"}] ; Event
;   8000 ; Timeout
;   ;; Optional callback:
;   (fn [reply] ; Reply is arbitrary Clojure data
;     (if (sente/cb-success? reply) ; Checks for :chsk/closed, :chsk/timeout, :chsk/error
;       (do-something! reply)
;       (error-handler!))))


;;.
