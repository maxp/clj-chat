
(ns fv.state
  (:require
    [reagent.core :as r]))
;

(def LAYOUT_LOGIN :login)
(def LAYOUT_FRAME :frame)

(defonce *layout-selector
  (r/atom LAYOUT_LOGIN))


(defn set-layout [layout]
  (reset! *layout-selector layout))
;

(defonce authtok (atom nil))

(defonce login-page (r/atom nil))

(comment
  { :msg ""
    :msg-class "ok | err"})


;;.
