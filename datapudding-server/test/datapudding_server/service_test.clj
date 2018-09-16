(ns datapudding-server.service-test
  (:require [io.pedestal.http :as http]
            [datapudding-server.service :refer [service-map]]))

(def service
  (::http/service-fn (http/create-servlet (http/create-server service-map))))

