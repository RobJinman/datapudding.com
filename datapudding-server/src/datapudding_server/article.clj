(ns datapudding-server.article
  (:require [clojure.java.jdbc :as jdbc]
            [ring.util.response :as ring-resp]
            [datapudding-server.db :as db]))

(defn get-article
  [request]
  (let [res (jdbc/query db/db ["SELECT * FROM dp.article"])]
    (println "Result of query:")
    (println res)
    (ring-resp/response "This is an article")))

