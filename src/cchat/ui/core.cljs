(ns cchat.ui.core
  (:require
    [reagent.core :as r]
    ;
    ;[cchat.ui.state :refer [*nick *text *chat]]
    [cchat.ui.frame :refer [screen-split]]))
;

(enable-console-print!)


(defn ^:export run []
  (.log js/console "run.")
  (r/render
    [screen-split]
    (js/document.getElementById "app-root")))
;

;;.
