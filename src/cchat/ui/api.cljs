
(ns fv.api
  (:require
    [ajax.core :refer [GET POST]]
    ;
    [fv.state :refer [authtok]]))
;


(defn api-get [url params handler error-handler]
  (GET url
    { :params params
      :oauth-token @authtok
      :format :json
      :response-format :json
      :keywords? true
      :timeout 5000
      :handler handler
      :error-handler error-handler}))
;

(defn api-post [url params handler error-handler]
  (POST url
    { :params params
      :oauth-token @authtok
      :format :json
      :response-format :json
      :keywords? true
      :timeout 5000
      :handler handler
      :error-handler error-handler}))
;

;;.
