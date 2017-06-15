
(ns cchat.ui.state
  (:require
    [reagent.core :as r]))
;

(defonce *nick (r/atom nil))
(defonce *text (r/atom nil))
(defonce *chat (r/atom nil))

;;.
