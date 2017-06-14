(ns fv.core
  (:require
    [reagent.core :as r]
    ;
    [fv.state :refer
        [*layout-selector LAYOUT_LOGIN LAYOUT_FRAME]]
    [fv.login :refer [login-view]]
    [fv.frame :refer [frame-view]]))
;

(enable-console-print!)


(defn select-layout []
  (condp = @*layout-selector
    ;
    LAYOUT_LOGIN      (login-view)
    ;
    LAYOUT_FRAME      (frame-view)
    ;
    ;; error!
    [:div.jumbotron
      [:h2.text-center
        "Program Error!"]
      [:br][:br]
      [:p "Unexpected application state."]
      [:br][:br]
      [:br]]))
;


(defn ^:export run []
  (.log js/console "run.")
  (r/render
    [select-layout]
    (js/document.getElementById "app-root")))
;

;;.
