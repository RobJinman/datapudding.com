(ns datapudding-server.article
  (:require [ring.util.response :as ring-resp]))

(defn get-article
  [request]
  (ring-resp/response "This is an article"))

