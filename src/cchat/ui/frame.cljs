
(ns fv.frame)


(defn frame-view []
  [:div.f0-split
    [:div.f0-top
      [:div.topbar
        "top menu"]]
    [:div.f0-mid
      [:div.f1-side
        [:div.f2-top
          "side bar 1"]
        [:div.f2-bottom
          "side bar 2"]]
      ;
      [:div.f1-pane
        [:div.main-pane
          [:h2 "main pane"]]]]])
;

;;.
