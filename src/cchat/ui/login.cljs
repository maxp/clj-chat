
(ns fv.login
  (:require
    [reagent.core :as r]
    [ajax.core :refer [POST]]
    ;
    [fv.state :refer
      [set-layout LAYOUT_FRAME authtok login-page]]))
;


(def LOGIN_URL "/auth/login")


(defonce *form (r/atom {}))


(defn log [msg param]
  (.log js/console msg (clj->js param)))
;

(defn get-val [id]
  (get @*form id))
;

(defn set-val! [id val]
  (swap! *form assoc id val))
;


(defn do-login [evt]
  (.preventDefault evt)
  (POST LOGIN_URL
    { :params
        { :login    (get-val :login)
          :password (get-val :password)}
      :format :json
      :response-format :json
      :keywords? true
      :timeout 5000
      :handler
        (fn [resp]
          (if (:ok resp)
            (do
              (.log js/console "login-ok:" (clj->js resp))
              ;
              (reset! authtok (:authtok resp))
              (set-val! :password nil)
              (swap! login-page
                assoc
                  :msg "Пользователь авторизован."
                  :msg-class "msg_ok")
              (set-layout LAYOUT_FRAME))
            ;;
            (do
              (swap! login-page
                assoc
                  :msg (:msg resp)
                  :msg-class "msg_err"))))
        ;
      :error-handler
        (fn [err]
          (.error js/console "do-login:" (clj->js err)))})
  false)
;



(defn login-view []
  [:div.container

    [:br]
    [:br]
    [:h2.text-center "Вход в систему"]
    [:br]

    [:div.col-md-6.col-md-offset-3
      [:form.form
        {:on-submit do-login}

        [:div.form-group
          [:label {:for "login"} "Логин:"]
          [:input#login.form-control
            { :type "text"
              :value (get-val :login)
              :on-change
                #(set-val! :login (-> % .-target .-value))}]]
        ;
        [:div.form-group
          [:label {:for "password"} "Пароль:"]
          [:input#password.form-control
            { :type "password"
              :on-change
                #(set-val! :password (-> % .-target .-value))}]]
        ;
        [:div.form-group
          {:style {:margin-top "20px"}}
          [:button.btn.btn-success
            { :type "submit" :on-click do-login}
            [:b "Войти"]]]
        ;
        [:div.text-center
          (when-let [msg (-> @login-page :msg not-empty)]
            [:div {:class (str "msg " (:msg-class @login-page))}
              msg])]]]])
          ;
;


;;.
