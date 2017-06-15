
(ns cchat.ui.frame
  (:require
    [cchat.ui.state :refer [*nick *text *chat]]))
;


(defn send-text [evt]
  (.preventDefault evt)

  (.log js/console "send-text:" (clj->js @*nick) (clj->js @*text))

  (reset! *text "")

  false)
;


(defn render-chat []
  [:div.chat-pane
    "chat-pane"])
;

(defn render-ctrl []
  [:div.chat-pane
    [:div.container
      [:form.form {:on-submit send-text}
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
            {:type "submit" :on-click send-text}
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
