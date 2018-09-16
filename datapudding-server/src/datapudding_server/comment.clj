(ns datapudding-server.comment
  (:require [ring.util.response :as ring-resp]))

(defn get-comment
  [request]
  (ring-resp/response "This is a comment"))

