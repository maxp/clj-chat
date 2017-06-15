
(ns cchat.ui.frame
  (:require
    [clojure.string :refer [blank?]]
    [cchat.ui.state :refer [*nick *text *chat]]
    [cchat.ui.trans :refer [send-text]]))
;


(defn on-submit [evt]
  (.preventDefault evt)
  (let [nick @*nick
        text @*text]
    (when-not (or (blank? nick) (blank? text))
      (send-text nick text))
    ;
    (reset! *text ""))
  false)
;


(defn render-chat []
  [:div.chat-pane
    (for [msg @*chat]
      ^{:key (:id msg)}
      [:div.msg
        [:div.nick (:nick msg)]
        [:div.text (:text msg)]])])
;

(defn render-ctrl []
  [:div.chat-pane
    [:div.container
      [:form.form {:on-submit on-submit}
        [:div.col-sm-2
          [:input.form-control
            { :type "text"
              :placeholder "nick"
              :value @*nick
              :on-change
                #(reset! *nick (-> % .-target .-value))}]]

        [:div.col-sm-8
          [:input.form-control
            { :type "text"
              :placeholder "text"
              :value @*text
              :on-change
                #(reset! *text (-> % .-target .-value))}]]

        [:div.col-sm-2
          [:button.btn.btn-success.form-control
            {:type "submit" :on-click on-submit}
            "send"]]]
      [:div.clearfix]]])
;


(defn screen-split []
  [:div.v-flex
    [:div.b-chat
      [render-chat]]
    [:div.b-ctrl
      [render-ctrl]]])
;


;;.
