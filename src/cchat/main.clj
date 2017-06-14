
(ns cchat.main
  (:require
    [mount.core :refer [start-with-args]]
    [mlib.conf :refer [conf]]
    [mlib.log :refer [error]]
    [mlib.core :refer [edn-read]]
    ;
    [cchat.app.chansock]
    [cchat.app.srv])
  (:gen-class))
;

(defn -main [& args]
  (if-let [rc (edn-read (first args))]
    (start-with-args rc)
    (error "config profile must be in parameters!")))
  ;

;;.
