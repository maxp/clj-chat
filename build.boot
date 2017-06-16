;;
;;  clj-chat
;;

(def project {:name "clj-chat" :version "0.0.1"})

(def jar-main   'cchat.main)
(def jar-file   "cchat.jar")
(def cljs-main  'cchat.ui.core/run)

(set-env!
  :resource-paths #{"res"}
  :source-paths #{"src"}
  :asset-paths #{"res"}

  ;; boot -d boot-deps ancient
  :dependencies
  '[
    [org.clojure/clojure "1.8.0"]
    [org.clojure/clojurescript "1.9.562"]
    [org.clojure/core.async "0.3.443"]

    ; [org.clojure/core.cache "0.6.4"]

    [org.clojure/tools.logging "0.4.0"]
    [ch.qos.logback/logback-classic "1.2.3"]

    [clj-time "0.13.0"]
    [clj-http "3.6.1"]

    [com.taoensso/sente "1.11.0"]

    [ring/ring-core "1.6.1"]
    [ring/ring-json "0.4.0"]
    [ring/ring-headers "0.3.0"]
    ; [ring/ring-jetty-adapter "1.6.1"]
    [http-kit "2.2.0"]
    [javax.servlet/servlet-api "2.5"]   ;; required by multipart_params

    [cheshire "5.7.1"]
    [compojure "1.6.0"]
    [hiccup "1.0.5"]
    [garden "1.3.2"]
    [mount "0.1.11"]

    ; [com.novemberain/monger "3.1.0"]

    ; [org.postgresql/postgresql "42.1.1"]

    ;; https://funcool.github.io/clojure.jdbc/latest/
    ; [funcool/clojure.jdbc "0.9.0"]
    ; [hikari-cp "1.7.5"]   ; https://github.com/tomekw/hikari-cp
    ; [honeysql "0.8.2"]    ; https://github.com/jkk/honeysql

    [reagent "0.6.2"]
    ; [reagent "0.6.0" :exclusions [cljsjs/react]]
    ; [cljsjs/react-with-addons "15.2.1-0"]

    ;;; cljs ;;;

    [cljs-ajax "0.6.0"]


    ;; protobuf

    [org.flatland/protobuf "0.7.1"]


    ;; https://github.com/martinklepsch/boot-garden
    [org.martinklepsch/boot-garden "1.3.2-0" :scope "test"]

    ;; cljs
    [adzerk/boot-cljs        "2.0.0"  :scope "test"]
    [adzerk/boot-reload      "0.5.1"  :scope "test"]
    ; [pandeiro/boot-http      "0.8.3"  :scope "test"]

    [adzerk/boot-cljs-repl   "0.3.3"  :scope "test"]
    [org.clojure/tools.nrepl "0.2.13" :scope "test"]
    [com.cemerick/piggieback "0.2.2"  :scope "test"]
    [weasel                  "0.7.0"  :scope "test"]

    ; [crisptrutski/boot-cljs-test "0.2.2-SNAPSHOT" :scope "test"

    ;; repl
    [org.clojure/tools.namespace "0.2.11" :scope "test"]
    [proto-repl "0.3.1" :scope "test"]])
;

;;;;;;;;;

(require
  '[clojure.tools.namespace.repl :refer [set-refresh-dirs refresh]]
  '[clojure.edn :as edn]
  '[clj-time.core :as tc]
  '[mount.core :as mount]
  '[org.martinklepsch.boot-garden :refer [garden]]
  '[adzerk.boot-cljs      :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl repl-env]]
  '[adzerk.boot-reload    :refer [reload]])
  ; '[pandeiro.boot-http    :refer [serve]])
;

(task-options!
  garden
  {
    :styles-var 'css.root/main
    :output-to  "public/incs/css/main.css"
    :pretty-print false})
;

;;; ;;; ;;; ;;;

(defn start []
  (require jar-main)
  (-> "conf/dev.edn"
    (slurp)
    (edn/read-string)
    (mount/start-with-args)))
;

(defn go []
  (mount/stop)
  (apply set-refresh-dirs (get-env :source-paths))
  (refresh :after 'boot.user/start))
;

;;; ;;; ;;; ;;;

(defn increment-build []
  (let [bf "res/build.edn"
        num (:num (edn/read-string (slurp bf)))
        bld { :timestamp (str (tc/now))
              ;:commit (last-commit)
              :num (inc num)}]
    (spit bf (.toString (merge project bld)))))
;

(deftask test-env []
  (set-env! :source-paths #(conj % "test"))
  identity)
;

(deftask dev []
  (set-env! :source-paths #(conj % "test"))
  (apply set-refresh-dirs (get-env :source-paths))
  ;;
  (create-ns 'user)
  (intern 'user 'reset
    (fn []
      (prn "(user/reset)")
      ((resolve 'boot.user/go))))
  ;;
  (comp
    ; (javac)
    identity))
;

(deftask css-dev []
  (set-env!
    :resource-paths #{}
    :asset-paths #{})
  (comp
    (garden
      :pretty-print true
      :output-to "main.css")))
    ; (sift
    ;   ; :invert true
    ;   :include #{#".+css$"})
    ;(target :dir #{"tmp/garden"})))
;

;; https://github.com/adzerk-oss/boot-cljs-repl

;; https://github.com/magomimmo/modern-cljs

(deftask js-dev []
  (set-env!
    :resource-paths #{}
    :asset-paths #{"res/public"})
  (comp
    (watch)
    (reload :on-jsload cljs-main)
    ; (cljs-repl)
    (cljs
          :source-map true
          :optimizations :none)
    (garden
          :pretty-print true
          :output-to "incs/css/main.css")
    (target
          :dir #{"tmp/devroot"})))
;

;; (sift :include #{#"\.out"} :invert true)


;; https://github.com/magomimmo/modern-cljs
;; https://github.com/magomimmo/modern-cljs/blob/master/doc/second-edition/tutorial-20.md
;; https://github.com/magomimmo/modern-cljs/blob/master/doc/second-edition/tutorial-21.md
;; https://github.com/magomimmo/modern-cljs/blob/master/doc/second-edition/tutorial-22.md

;; cljs
; (sift :include #{#"\.out"} :invert true)
;
; boot watch speak cljs
; boot cljs -sO advanced -- target
; boot serve -d target/ watch speak reload cljs-repl cljs -sO none
;
;; - https://github.com/boot-clj/boot-cljs/wiki/Usage
;; - https://github.com/adzerk-oss/boot-cljs-example
;
; https://github.com/Deraen/saapas
; https://github.com/martinklepsch/tenzing
;


(deftask cljs-build []
  (comp
    (cljs :source-map false
          :optimizations :advanced)
    (sift :invert true :include #{#"\.out/.*"})
    (sift :move {#"incs/js/main.js$" "public/incs/js/main.js"})))
;


(deftask build []
  (increment-build)
  (comp
    (cljs-build)
    ;(javac)
    (garden)
    (aot :all true)
    (uber)
    (jar :file jar-file :main jar-main)
    (target :dir #{"tmp/target"})))
;


;;.
