(ns datapudding-server.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db {:dbtype "postgresql"
         :dbname "dpdb"
         :host "localhost"
         :port 5432
         :user "dpapp"
         :password "password"
         :ssl true
         :sslfactory "org.postgresql.ssl.NonValidatingFactory"})


