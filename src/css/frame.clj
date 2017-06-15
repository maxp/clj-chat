
(ns css.frame
  (:refer-clojure :exclude [>])
  (:require
    [garden.units :refer [px pt em ex]]
    [garden.selectors :refer [>]]))
;    [garden.stylesheet :refer [at-media]]))
;


(def frame-styles
  [
    [:.b-chat
      {
        :flex "1"
        :display "flex"
        :flex-direction "column"
        :position "relative"
        ;
        :background-color "#48c"}
      [:.chat-pane
        { :position "absolute"
          :top "14px"
          :left "14px"
          :right "14px"
          :bottom "14px"
          :overflow-y "scroll"
          :background-color "#f4f4f4"
          :border-radius "2px"}
        [:.nick
          { :font-weight "bold"
            :color "#888"}]
        [:.text
          { :color "#000"
            :margin "0 4px 4px 6px"}]]]
    ;
    [:.b-ctrl
      {
        :flex "0 1 auto"
        :min-height "40px"
        :padding "10px"
        ;;
        :background-color "#eee"}]])
    ;

;       {
;         :height "90px"
;         :min-height "90px"
;         :flex "0"
;         :padding "10px"
;         :background-color "#aa8844"}
;       ;
;       [:.topbar
;         { :height "100%"
;           :background-color "#ff4444"}]]
;
;     [:.f0-mid
;       { :flex "1"
;         :display "flex"
;         :flex-direction "row"
;         :background-color "#48c"}]
;
;     [:.f1-side
;       {
;         :flex "0"
;         :display "flex"
;         :flex-direction "column"
;         :width "200px"
;         :height "100%"
;         :min-width "200px"
;         :background-color "#ddd"}]
;
;     [:.f2-top
;       { :flex "0"
;         :min-height "100px"
;         :background-color "#eee"}]
;     [:.f2-bottom
;       { :flex "1"
;         :height "100px"
;         :background-color "#a84"}]
;
;     [:.f1-pane
;       {:flex "1"
;         :padding "6px"
;         :position "relative"
;         :background-color "#0a8"}]])
; ;
;
; (def main-pane
;   [:.main-pane
;     { :position "absolute"
;       :top (px 6)
;       :left (px 6)
;       :right (px 6)
;       :bottom (px 6)
;       :overflow "auto"}])
;       ; :width "100%"
;       ; :height "100%"}])
; ;

;;.
