
(ns cchat.ui.trans
  ; (:require-macros
  ;   [cljs.core.async.macros :as asyncm :refer [go go-loop]])
  ;
  (:require
    [clojure.walk :refer [keywordize-keys]]
    [cljs.core.async :as async :refer [<! >! put! chan]]
    [goog.crypt.base64 :refer [encodeByteArray decodeStringToUint8Array]]
    [taoensso.sente :refer
        [make-channel-socket! start-client-chsk-router! cb-success?]]
    ;
    [cchat.ui.state :refer [*nick *text *chat]]))
;

(enable-console-print!)


;; NOTE: duplicated constant in app/routes.clj
(def WS_CHAT_URI "/_ws_chat")


(defn obj->clj [obj]
  (-> (fn [result key]
        (let [v (aget obj key)]
          (if (= "function" (goog/typeOf v))
            result
            (assoc result key v))))
      (reduce {} (.getKeys goog/object obj))))
;

(let [{:keys [chsk ch-recv send-fn state]}
      (make-channel-socket! WS_CHAT_URI {:type :auto})]
  ;
  (def chsk       chsk)
  (def ch-chsk    ch-recv) ; ChannelSocket's receive channel
  (def chsk-send! send-fn) ; ChannelSocket's send API (fn [event & [?timeout-ms ?cb-fn]]))
  (def chsk-state state))   ; Watchable, read-only atom

;

;; protobuf.roots.default.ChatMessage
(def ChatMessage (.. js/protobuf -roots -default -ChatMessage))


(defn send-text [nick text]
  (.log js/console "send-text:" nick text)
  (let [cm (.create ChatMessage (clj->js {:nick nick :text text}))
        buff (.encode ChatMessage cm)
        buff (.finish buff)
        buff64 (encodeByteArray buff)]
        ;; NOTE: yes, that is a hack to make sente happy
    ;
    (chsk-send! [:trans/buff64 {:b64 buff64}])))
  ; (chsk-send! [:trans/chat {:nick nick :text text}]))
;

(defonce *msg-id (atom 0))


(defn recv-msg [data]
  (when (= :chsk/recv (:id data))
    (let [event  (:event data)
          buff64 (get-in event [1 1 :b64])
          buff   (decodeStringToUint8Array buff64)
          obj    (.decode ChatMessage buff)
          cm     (keywordize-keys (obj->clj obj))]
      ;
      (swap! *chat conj
        (assoc cm :id (swap! *msg-id inc))))))
;

(defonce router
  (start-client-chsk-router! ch-chsk recv-msg))



; (chsk-send! ; Using Sente
;   [:some/request-id {:name "Rich Hickey" :type "Awesome"}] ; Event
;   8000 ; Timeout
;   ;; Optional callback:
;   (fn [reply] ; Reply is arbitrary Clojure data
;     (if (sente/cb-success? reply) ; Checks for :chsk/closed, :chsk/timeout, :chsk/error
;       (do-something! reply)
;       (error-handler!))))


;;.
